Algoritmo CasoPractico1
		
	Definir inputNum Como Entero    //Variable que contendr� el n�mero introducido por el usuario
	Definir cocienteInputNum Como Real //Variable que almacenar� el resultado de operar con inputNum que puede tener decimales
	Definir numeroDeDigitos Como Entero //Variable que servir� para contar el n�mero de d�gitos del n�mero introducido por el usuario
	Definir finalizarPrograma Como Logico  //Variable que se usar� para controlar si repetimos todo el algoritmo principal o no
	Definir numeroCorrecto Como Logico //Variable que se usar� para controlar si el n�mero est� dentro del rango 
	Definir numeroDeDigitosCorrecto Como Logico //Variable que se usar� para controlar cuando tenemos el n�mero de d�gitos correcto
	Definir letraParaFinalizar Como Caracter //Variable para almacenar la opci�n elegida por el usuario al finalizar el cuerpo del algoritmo
	
	//Inicializamos un bucle repetir, porque al menos se ejecutar� una vez, que ser� el responsable de evaluar 
	//la variable l�gica finalizarPrograma, que en el caso de que sea falsa, volver� a ejecutar todo el cuerpo del 
	//algoritmo principal, en el caso contrario, el algoritmo finalizar�.
	
	Repetir
		//Inicializamos las variables aqu� y no justo despu�s de la declaraci�n de variables, para que en el caso de repetirse 
		//el algoritmo principal, la l�gica no falle por estar en un valor no controlado de la anterior ejecuci�n
		
		inputNum <- 0
		numeroDeDigitos <- 0
		cocienteInputNum <- 0
		finalizarPrograma <- Falso
		numeroCorrecto <- Falso
		numeroDeDigitosCorrecto <- Falso
		letraParaFinalizar <- " "
		
		//Inicializamos otro bucle repetir, porque al menos se ejecutar� una vez, que ser� el responsable
		//de ejecutar continuamente la petici�n del n�mero a introducir, hasta que la variable numeroCorrecto cambie a Verdadero
		
		Repetir
			Escribir "Introduce un n�mero entero entre el 0 y el 99999, gracias: "  //Pedimos al usuario el valor de un n�mero por pantalla
			Leer inputNum  //Asignamos el valor le�do por pantalla a la variable inputNum
			
			//Evaluamos con un condicional si el n�mero introducido cumple con los requisitos exigidos,
			//en caso afirmativo la variable numeroCorrecto cambiar� a verdadero y el bucle no se repetir�
			//en caso contrario la variable seguir� Falso, y el bucle repetir� la petici�n
			
			si inputNum < 0 o inputNum > 99999
				Escribir "El n�mero introducido no es correcto"
				Escribir ""
			SiNo
				numeroCorrecto <- Verdadero
			FinSi
			
		Hasta Que numeroCorrecto = Verdadero 
		
		//Asignamos a cocienteInputNum el valor de inputNum para poder operar con el valor de inputNum sin perder el dato original
		cocienteInputNum <- inputNum 
		
		//Inicializamos un bucle mientras que sirva para evaluar la condici�n de la variable numeroDeDigitosCorrecto 
		//que terminar� cuando se cumplan los requisitos y esta tome el valor verdadero
		
		Mientras numeroDeDigitosCorrecto = Falso Hacer
			//vamos diviendo el valor de cocienteInputNum y los asignamos a si mismo para quedarnos con la parte con un digito menos
			//as� que el n�mero se ir� haciendo m�s corto en cada iteraci�n del bucle hasta que no cumpla la condici�n 
			
			cocienteInputNum <- cocienteInputNum / 10 
			
			//Vamos acumulando el n�mero de iteraciones en la variable numeroDeDigitos asignada a si misma
			//y sum�ndole uno en cada vuelta. 
			
			numeroDeDigitos <- numeroDeDigitos + 1
			
			//Evaluamos la condici�n de fin de bucle, cuando quede un solo d�gito poniendo la variable numeroDeDigitosCorrecto en verdadero
			//y por lo tanto haciendo que la condici�n del bucle sea Falsa y este no se ejecute m�s
			
			Si cocienteInputNum < 1
				numeroDeDigitosCorrecto <- Verdadero
			FinSi
			
		FinMientras
		
		//Toda la l�gica de c�lculo de d�gitos podr�a ser m�s eficiente utilizando funciones logar�tmicas, pero en la unidad 1 no hemos cubierto la parte de funciones 
		//y no nos parec�a correcto usarlas para resolverlo por este m�todo. Al igual que otras funciones de control de datos.
		
		Escribir "El n�mero introducido tiene ", numeroDeDigitos, " cifras" //Escribe el resultado de los digitos entre dos cadenas de caracteres
		Escribir "Quiere usted continuar? Pulse s para continuar, o cualquier tecla para finalizar: "
		Leer letraParaFinalizar
		
		//Comprobamos si la cadena de caracteres introducido cumple los requisitos para finalizar el programa o continuar
	
		Si letraParaFinalizar <> "s"
			finalizarPrograma <- Verdadero
		FinSi
	Hasta Que finalizarPrograma = Verdadero
FinAlgoritmo

