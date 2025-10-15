// BubbleImperativa.scala
@main def bubbleImperativa(): Unit =
  var listaNumeros = Array(19, 8, 12, 3, 10)
  println(s"Antes: ${listaNumeros.mkString(", ")}")

  // Algoritmo clásico: dos bucles anidados
  for i <- 0 until listaNumeros.length - 1 do
    for j <- 0 until listaNumeros.length - i - 1 do
      if listaNumeros(j) > listaNumeros(j + 1) then
        // swap
        val tmp = listaNumeros(j)
        listaNumeros(j) = listaNumeros(j + 1)
        listaNumeros(j + 1) = tmp

  println(s"Después: ${listaNumeros.mkString(", ")}")
