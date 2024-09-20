Algoritmo NumerosPrimos
	Definir N Como Entero
	Definir m Como Entero
	Definir error Como Logico
	Definir primo Como Logico
	N <- 0
	m <- 2
	error <- Falso
	primo <- Verdadero
	Repetir
		Escribir "Introduce un número entero positivo: "
		Escribir ""
		Leer N
		Si (N < 0) Entonces
			
			Escribir "El número introducido no es positivo"
			Escribir ""
			error <- Verdadero
		SiNo
			error <- Falso
		FinSi
	Hasta Que NO (error)
	Si (N < 2) Entonces
		Escribir "Ese número no es primo"
	Sino
		Mientras (m <= N) Hacer
			primo <- Verdadero
			Si (m = 2) Entonces  
				Escribir m, " "
			Sino
				Para i <- 2 Hasta m - 1 Con Paso 1 Hacer
					Si (m MOD i = 0) Entonces
						primo <- Falso
//						Escribir "Ese número no es primo"
						i <- m - 1  // Termina el bucle
					FinSi
				Fin Para
				Si (primo) Entonces
					Escribir m, " "
				FinSi
			FinSi
		m <- m + 1
		Fin Mientras
	FinSi
Fin Algoritmo

