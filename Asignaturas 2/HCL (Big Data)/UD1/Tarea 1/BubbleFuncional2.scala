object BubbleSortFuncional {
  def pasarBurbuja(listaNumeros: List[Int]): (List[Int], Boolean) = listaNumeros match {
    case Nil | List(_) => (listaNumeros, false)
    case x :: y :: tail =>
      if (x > y) {
        val (rest, _) = pasarBurbuja(x :: tail)
        (y :: rest, true)
      } else {
        val (rest, swapped) = pasarBurbuja(y :: tail)
        (x :: rest, swapped)
      }
  }

  def ordenacionBurbuja(listaNumeros: List[Int]): List[Int] = {
    val (nuevaLista, cambio) = pasarBurbuja(listaNumeros)
    if (!cambio) nuevaLista
    else ordenacionBurbuja(nuevaLista)
  }

  def main(args: Array[String]): Unit = {
    val listaDesordenada = List(19, 8, 12, 3, 10)
    println(s"Lista original (Inmutable): $listaDesordenada")
    val listaOrdenada = ordenacionBurbuja(listaDesordenada)
    println(s"Lista ordenada (Funcional): $listaOrdenada")
  }
}
