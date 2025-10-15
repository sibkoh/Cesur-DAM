object BubbleSortFuncional {

  /**
    * Función 1: Realiza una única pasada (un 'sweep') de la burbuja.
    * Mueve el elemento más grande de la sub-lista al final.
    * * @param xs La lista de entrada.
    * @return La lista después de una pasada, más una señal (booleana)
    * que indica si hubo algún intercambio (si la lista cambió).
    */
  def pasarBurbuja(xs: List[Int]): (List[Int], Boolean) = xs match {
    
    // CASO BASE 1: Lista vacía o con un solo elemento. No hay nada que comparar.
    // La lista está en orden (localmente), y no hubo cambios.
    case Nil | List(_) => (xs, false) 
      
    // CASO RECURSIVO 1: Desestructuramos (x y y son los dos primeros elementos)
    case x :: y :: tail => 
      if (x > y) {
        // Intercambio (Simulado): Si el de la izquierda (x) es mayor que el de la derecha (y)
        
        // 1. Ponemos 'y' primero (el menor).
        // 2. Llamamos recursivamente con 'x' (el mayor) y el resto de la lista (tail).
        // El resultado es una NUEVA LISTA, y decimos 'true' (hubo cambio).
        val (rest, _) = pasarBurbuja(x :: tail)
        (y :: rest, true)

      } else {
        // No hay intercambio: El orden es correcto (x <= y).
        
        // 1. Ponemos 'x' primero (el menor).
        // 2. Llamamos recursivamente con 'y' y el resto de la lista (tail).
        // Conservamos el estado de cambio que viene de la recursión.
        val (rest, swapped) = pasarBurbuja(y :: tail)
        (x :: rest, swapped)
      }
  }

  /**
    * Función 2: Controla las pasadas múltiples hasta que no haya cambios.
    * * @param xs La lista de entrada.
    * @return La lista completamente ordenada.
    */
  def ordenacionBurbuja(xs: List[Int]): List[Int] = {
    // 1. Ejecutamos una pasada de la burbuja. Recibimos la nueva lista y la bandera 'cambio'.
    val (nuevaLista, cambio) = pasarBurbuja(xs)
    
    // 2. COMPROBACIÓN (Caso Base de esta recursión):
    if (!cambio) {
      // Si 'cambio' es falso, ¡la lista está ordenada! 
      // Devolvemos el resultado final.
      nuevaLista
    } else {
      // Si hubo cambios, significa que necesitamos OTRA pasada.
      // Llamamos a la función de nuevo con la 'nuevaLista' para repetir el proceso.
      ordenacionBurbuja(nuevaLista)
    }
  }

  // Punto de entrada de la aplicación
  def main(args: Array[String]): Unit = {
    val listaDesordenada = List(19, 8, 12, 3, 10)
    println(s"Lista original (Inmutable): $listaDesordenada")
    
    val listaOrdenada = ordenacionBurbuja(listaDesordenada)
    println(s"Lista ordenada (Funcional): $listaOrdenada")
  }
}