package com.lexludi.ludigest_backend.service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.lexludi.ludigest_backend.dto.BggGameDetailsDto;
import com.lexludi.ludigest_backend.dto.BggSearchDto;
import com.lexludi.ludigest_backend.model.JuegoReferencia;
import com.lexludi.ludigest_backend.repository.JuegoReferenciaRepository;

@Service
public class BggSyncService {

    // Inyectamos el token de seguridad desde application.properties
    @Value("${bgg.api.token}")
    private String bggApiToken;

    private final RestTemplate restTemplate = new RestTemplate();
    
 // 1. Declaramos la herramienta que necesitamos
    private final JuegoReferenciaRepository juegoReferenciaRepository;

    // 2. Creamos el constructor para que Spring nos la "inyecte" automáticamente
    public BggSyncService(JuegoReferenciaRepository juegoReferenciaRepository) {
        this.juegoReferenciaRepository = juegoReferenciaRepository;
    }

    // --- METODO DE BUSQUEDA (API V1) ---
    public List<BggSearchDto> buscarJuegos(String query) {
        String url = "https://boardgamegeek.com/xmlapi/search?search=" + query;
        String xmlResponse = hacerPeticionConToken(url);
        List<BggSearchDto> resultados = new ArrayList<>();
        
        try {
            Document doc = parsearXml(xmlResponse);
            NodeList items = doc.getElementsByTagName("boardgame");

            for (int i = 0; i < items.getLength(); i++) {
                Element item = (Element) items.item(i);
                BggSearchDto dto = new BggSearchDto();
                
                dto.setBggId(Long.parseLong(item.getAttribute("objectid")));
                
                NodeList nameNodes = item.getElementsByTagName("name");
                if (nameNodes.getLength() > 0) {
                    dto.setTitulo(nameNodes.item(0).getTextContent());
                }
                
                NodeList yearNodes = item.getElementsByTagName("yearpublished");
                if (yearNodes.getLength() > 0) {
                    try {
                        dto.setAnoPublicacion(Integer.parseInt(yearNodes.item(0).getTextContent()));
                    } catch (NumberFormatException e) {
                        dto.setAnoPublicacion(null);
                    }
                }
                resultados.add(dto);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error procesando XML de busqueda: " + e.getMessage());
        }
        return resultados;
    }

    // --- METODO DE DETALLES (API V1) ---
    public BggGameDetailsDto obtenerDetallesJuego(Long bggId) {
        String url = "https://boardgamegeek.com/xmlapi/boardgame/" + bggId + "?stats=1";
        String xmlResponse = hacerPeticionConToken(url);
        BggGameDetailsDto dto = new BggGameDetailsDto();
        dto.setBggId(bggId);

        try {
            Document doc = parsearXml(xmlResponse);
            NodeList items = doc.getElementsByTagName("boardgame");
            
            if (items.getLength() > 0) {
                Element item = (Element) items.item(0);

                if (item.getElementsByTagName("thumbnail").getLength() > 0) {
                    dto.setUrlImagen(item.getElementsByTagName("thumbnail").item(0).getTextContent());
                }
                if (item.getElementsByTagName("description").getLength() > 0) {
                    dto.setDescripcion(item.getElementsByTagName("description").item(0).getTextContent());
                }

                NodeList nameNodes = item.getElementsByTagName("name");
                for (int i = 0; i < nameNodes.getLength(); i++) {
                    Element nameEl = (Element) nameNodes.item(i);
                    if ("true".equals(nameEl.getAttribute("primary"))) {
                        dto.setTitulo(nameEl.getTextContent());
                        break;
                    }
                }

                dto.setAnoPublicacion(extraerEnteroV1(item, "yearpublished"));
                dto.setMinJugadores(extraerEnteroV1(item, "minplayers"));
                dto.setMaxJugadores(extraerEnteroV1(item, "maxplayers"));
                dto.setDuracionMinutos(extraerEnteroV1(item, "playingtime"));

                NodeList weightNodes = item.getElementsByTagName("averageweight");
                if (weightNodes.getLength() > 0) {
                    dto.setDureza(Double.parseDouble(weightNodes.item(0).getTextContent()));
                }

                NodeList categoriasNodes = item.getElementsByTagName("boardgamecategory");
                for (int i = 0; i < categoriasNodes.getLength(); i++) {
                    dto.getCategorias().add(categoriasNodes.item(i).getTextContent());
                }

                NodeList mecanicasNodes = item.getElementsByTagName("boardgamemechanic");
                for (int i = 0; i < mecanicasNodes.getLength(); i++) {
                    dto.getMecanicas().add(mecanicasNodes.item(i).getTextContent());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error procesando XML de detalles: " + e.getMessage());
        }
        return dto;
    }

    // --- METODOS PRIVADOS DE AYUDA ---

    private String hacerPeticionConToken(String url) {
        HttpHeaders headers = new HttpHeaders();
        // Construimos el header "Authorization: Bearer [TOKEN]"
        headers.set("Authorization", "Bearer " + bggApiToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }

    private Document parsearXml(String xmlStr) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xmlStr));
        return builder.parse(is);
    }
    
    private Integer extraerEnteroV1(Element parent, String tagName) {
        NodeList nodes = parent.getElementsByTagName(tagName);
        if (nodes.getLength() > 0) {
            String value = nodes.item(0).getTextContent();
            if (value != null && !value.trim().isEmpty()) {
                try {
                    return Integer.parseInt(value.trim());
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        }
        return null;
    }
    
 // --- IMPORTACION PESADA Y PERSISTENCIA ---
    @Transactional
    public JuegoReferencia importarJuegoDesdeBgg(Long bggId) {
        // 1. Obtener los detalles completos desde la BGG (reutilizamos la lógica de red existente)
        String url = "https://boardgamegeek.com/xmlapi/boardgame/" + bggId + "?stats=1";
        String xmlResponse = hacerPeticionConToken(url);
        
        try {
            Document doc = parsearXml(xmlResponse);
            Element item = (Element) doc.getElementsByTagName("boardgame").item(0);
            
         // Usamos el repositorio que inyectamos arriba
         // 2. Control de duplicados: ¿Ya lo tenemos?
            JuegoReferencia juego = juegoReferenciaRepository.findByIdBgg(bggId).orElse(new JuegoReferencia());
                           
            // 3. Mapeo Total a la Entidad
            juego.setIdBgg(bggId);
            
            // Titulo Primario
            NodeList names = item.getElementsByTagName("name");
            for (int i = 0; i < names.getLength(); i++) {
                Element nameEl = (Element) names.item(i);
                if ("true".equals(nameEl.getAttribute("primary"))) {
                    juego.setTitulo(nameEl.getTextContent());
                    break;
                }
            }

            // Datos técnicos
            juego.setAnoPublicacion(extraerEnteroV1(item, "yearpublished"));
            juego.setMinJugadores(extraerEnteroV1(item, "minplayers"));
            juego.setMaxJugadores(extraerEnteroV1(item, "maxplayers"));
            juego.setDuracionMinutos(extraerEnteroV1(item, "playingtime"));
            juego.setDescripcion(item.getElementsByTagName("description").item(0).getTextContent());
            
            if (item.getElementsByTagName("image").getLength() > 0) {
                juego.setUrlImagen(item.getElementsByTagName("image").item(0).getTextContent());
            }

            // Estadísticas (Puntuación y Dureza)
            if (item.getElementsByTagName("average").getLength() > 0) {
                juego.setPuntuacionBgg(Double.parseDouble(item.getElementsByTagName("average").item(0).getTextContent()));
            }
            if (item.getElementsByTagName("averageweight").getLength() > 0) {
                juego.setDureza(Double.parseDouble(item.getElementsByTagName("averageweight").item(0).getTextContent()));
            }

            // Jugadores recomendados (Poll Summary)
            NodeList pollResults = item.getElementsByTagName("result");
            for (int i = 0; i < pollResults.getLength(); i++) {
                Element res = (Element) pollResults.item(i);
                if ("bestwith".equals(res.getAttribute("name"))) {
                    juego.setJugadoresRecomendados(res.getAttribute("value"));
                }
            }

            // Mapeo de Listas (Categorías, Mecánicas, Autores) a Strings
            juego.setCategoria(unirEtiquetas(item, "boardgamecategory"));
            juego.setMecanicas(unirEtiquetas(item, "boardgamemechanic"));
            juego.setAutor(unirEtiquetas(item, "boardgamedesigner"));

         // Guardado final
            return juegoReferenciaRepository.save(juego);

        } catch (Exception e) {
            throw new RuntimeException("Error en la importación pesada: " + e.getMessage());
        }
    }

    // Método de ayuda para convertir múltiples tags XML en un String único
    private String unirEtiquetas(Element parent, String tagName) {
        NodeList nodes = parent.getElementsByTagName(tagName);
        List<String> lista = new ArrayList<>();
        for (int i = 0; i < nodes.getLength(); i++) {
            lista.add(nodes.item(i).getTextContent());
        }
        return String.join(", ", lista);
    }
}