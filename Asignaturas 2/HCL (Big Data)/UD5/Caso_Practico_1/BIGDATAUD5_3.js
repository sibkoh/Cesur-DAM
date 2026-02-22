db.web_pages.find({
    "contenido.cuerpo_texto": /restaurante/i,
    "localizacion_negocio": {
        $near: {
            $geometry: { type: "Point", coordinates: [-3.703790, 40.416775] }, // Coordenadas del usuario
            $maxDistance: 2000 // Busca solo en un radio de 2 kilómetros
        }
    }
})