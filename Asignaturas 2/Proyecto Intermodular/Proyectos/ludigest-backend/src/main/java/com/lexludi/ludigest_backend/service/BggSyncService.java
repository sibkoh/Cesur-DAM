package com.lexludi.ludigest_backend.service;

import java.io.InputStream;
import java.util.Optional;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.lexludi.ludigest_backend.model.JuegoReferencia;
import com.lexludi.ludigest_backend.repository.JuegoReferenciaRepository;

// Servicio encargado de la sincronizacion inteligente con la BGG y la integracion de datos
@Service
public class BggSyncService {

    // Inyectamos el repositorio para poder guardar en MySQL
    private final JuegoReferenciaRepository juegoReferenciaRepository;

    public BggSyncService(JuegoReferenciaRepository juegoReferenciaRepository) {
        this.juegoReferenciaRepository = juegoReferenciaRepository;
    }

    // Leemos el XML mock, lo mapeamos, validamos duplicados y lo guardamos en la base de datos
    public JuegoReferencia importarJuegoMock() {
        try {
            // 1. Cargamos el archivo de prueba desde resources (src/main/resources/mock/bgg_catan.xml)
            ClassPathResource resource = new ClassPathResource("mock/bgg_trickerion.xml");
            InputStream inputStream = resource.getInputStream();

            // 2. Preparamos las herramientas nativas de Java para leer XML (DOM Parser)
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document documento = builder.parse(inputStream);
            
            // Normalizamos la estructura del arbol XML para evitar errores de saltos de linea
            documento.getDocumentElement().normalize();

            // 3. Empezamos la extraccion buscando la etiqueta principal <item>
            NodeList itemList = documento.getElementsByTagName("item");
            if (itemList.getLength() > 0) {
                Element item = (Element) itemList.item(0);

                // Extraccion de ID de la BGG inmediatamente
                Long idBggXml = Long.parseLong(item.getAttribute("id"));

                // REGLA DE NEGOCIO: Validacion de duplicados
                // Buscamos en nuestra base de datos si ya tenemos este ID de la BGG
                Optional<JuegoReferencia> juegoExistente = juegoReferenciaRepository.findByIdBgg(idBggXml);

                if (juegoExistente.isPresent()) {
                    // Si el juego ya existe, lo devolvemos directamente y terminamos
                    // No hace falta extraer el resto de campos ni hacer el .save()
                    return juegoExistente.get();
                }

                // Si no existe, procedemos a crear el nuevo objeto y extraer el resto de datos
                JuegoReferencia nuevoJuego = new JuegoReferencia();
                nuevoJuego.setIdBgg(idBggXml);

                // Extraccion de la imagen (el texto que hay dentro de la etiqueta <thumbnail>)
                NodeList thumbnailList = item.getElementsByTagName("thumbnail");
                if (thumbnailList.getLength() > 0) {
                    nuevoJuego.setUrlImagen(thumbnailList.item(0).getTextContent());
                }

                // Extraccion del titulo primario (BGG tiene varios <name>, buscamos el type="primary")
                NodeList nameList = item.getElementsByTagName("name");
                for (int i = 0; i < nameList.getLength(); i++) {
                    Element nameElement = (Element) nameList.item(i);
                    if ("primary".equals(nameElement.getAttribute("type"))) {
                        nuevoJuego.setTitulo(nameElement.getAttribute("value"));
                        break; // Ya encontramos el principal, dejamos de buscar
                    }
                }

                // Extraccion de jugadores y tiempos (atributos "value")
                nuevoJuego.setMinJugadores(Integer.parseInt(((Element)item.getElementsByTagName("minplayers").item(0)).getAttribute("value")));
                nuevoJuego.setMaxJugadores(Integer.parseInt(((Element)item.getElementsByTagName("maxplayers").item(0)).getAttribute("value")));
                nuevoJuego.setDuracionMinutos(Integer.parseInt(((Element)item.getElementsByTagName("playingtime").item(0)).getAttribute("value")));

                // Extraccion de la puntuacion media (BGG la esconde dentro de <ratings><average value="..."/></ratings>)
                Element averageElement = (Element) item.getElementsByTagName("average").item(0);
                if (averageElement != null) {
                    nuevoJuego.setPuntuacionBgg(Double.parseDouble(averageElement.getAttribute("value")));
                }

                // --- NUEVAS EXTRACCIONES ---

                // 1. Dureza (Peso del juego)
                Element weightElement = (Element) item.getElementsByTagName("averageweight").item(0);
                if (weightElement != null) {
                    nuevoJuego.setDureza(Double.parseDouble(weightElement.getAttribute("value")));
                }

                // 2. Categoria (Buscamos el primer enlace de tipo boardgamecategory)
                NodeList linkList = item.getElementsByTagName("link");
                for (int i = 0; i < linkList.getLength(); i++) {
                    Element linkElement = (Element) linkList.item(i);
                    if ("boardgamecategory".equals(linkElement.getAttribute("type"))) {
                        nuevoJuego.setCategoria(linkElement.getAttribute("value"));
                        break; // Solo cogemos la primera categoria para simplificar
                    }
                }

                // 3. Jugadores recomendados
                // Para el entorno mock simplificamos la compleja estructura de la encuesta
                nuevoJuego.setJugadoresRecomendados("3");

                // PERSISTENCIA: Aqui es donde ocurre la magia. 
                // Guardamos el objeto en MySQL y el metodo nos lo devuelve con el ID asignado.
                return juegoReferenciaRepository.save(nuevoJuego);
            }

        } catch (Exception e) {
            // Si algo falla, avisamos claramente
            throw new RuntimeException("Error al procesar la importacion: " + e.getMessage());
        }
        
        return null;
    }
}