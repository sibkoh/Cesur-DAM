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
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.lexludi.ludigest_backend.dto.BggGameDetailsDto;
import com.lexludi.ludigest_backend.dto.BggSearchDto;

@Service
public class BggSyncService {

    // Inyectamos el token de seguridad desde application.properties
    @Value("${bgg.api.token}")
    private String bggApiToken;

    private final RestTemplate restTemplate = new RestTemplate();

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
}