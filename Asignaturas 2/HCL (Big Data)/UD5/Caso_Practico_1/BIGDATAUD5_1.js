// 1. Script de creación del índice (Setup inicial)
db.web_pages.createIndex(
  { 
    "contenido.titulo": "text", 
    "contenido.cuerpo_texto": "text",
    "metadatos_seo.keywords": "text",
    "dominio": "text"
  },
  { 
    weights: { 
      "contenido.titulo": 15,       // Prioridad máxima (si está en el título, puntúa más)
      "metadatos_seo.keywords": 10, // Prioridad alta
      "dominio": 5,                 // Prioridad media
      "contenido.cuerpo_texto": 1   // Prioridad normal (texto genérico)
    },
    default_language: "spanish" // Vital: aplica stemming (raíces) y elimina stopwords (el, la, los)
  }
)