// 2. Query de recuperación de resultados del usuario
db.web_pages.find(
    { $text: { $search: "despliegue 5G rural" } },
    { score: { $meta: "textScore" } } // Extrae la puntuación de relevancia que calcula MongoDB
)
    .sort({ score: { $meta: "textScore" } }) // Ordena los resultados de mayor a menor relevancia
    .limit(20) // Paginación básica: Solo traemos 20 resultados para no saturar la red