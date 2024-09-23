Algoritmo CasoPractico1
	//Declaramos las variables mínimas necesarias que necesitará el algoritmo 
	
	Definir inputNum Como Entero    //Variable que contendrá el número introducido por el usuario
	Definir cocienteInputNum Como Real //Variable que almacenará el resultado de operar con inputNum que puede tener decimales
	Definir numeroDeDigitos Como Entero //Variable que servirá para contar el número de dígitos del número introducido por el usuario
	Definir finalizarPrograma Como Logico  //Variable que se usará para controlar si repetimos todo el algoritmo principal o no
	Definir numeroCorrecto Como Logico //Variable que se usará para controlar si el número está dentro del rango 
	Definir numeroDeDigitosCorrecto Como Logico //Variable que se usará para controlar cuando tenemos el número de dígitos correcto
	Definir letraParaFinalizar Como Caracter //Variable para almacenar la opción elegida por el usuario al finalizar el cuerpo del algoritmo
	
	//Inicializamos un bucle repetir, porque al menos se ejecutará una vez, que será el responsable de evaluar 
	//la variable lógica finalizarPrograma, que en el caso de que sea falsa, volverá a ejecutar todo el cuerpo del 
	//algoritmo que está en su interior, en el caso contrario, el algoritmo finalizará.
	
	Repetir
		//Inicializamos las variables aquí y no después de la declaración de variables para que en el caso de repetirse 
		//el cuerpo del algoritmo, la lógica no falle por estar un valor no controlado de la anterior ejecución
		
		inputNum <- 0
		numeroDeDigitos <- 0
		cocienteInputNum <- 0
		finalizarPrograma <- Falso
		numeroCorrecto <- Falso
		numeroDeDigitosCorrecto <- Falso
		letraParaFinalizar <- " "
		
		//Inicializamos otro bucle repetir, porque al menos se ejecutará una vez, que será el responsable
		//de ejecutar continuamente la petición del número a introducir, hasta que la variable numeroCorrecto cambie a Verdadero
		
		Repetir
			Escribir "Introduce un número entero entre el 0 y el 99999, gracias: "  //Pedimos al usuario el valor de un número por pantalla
			Leer inputNum  //Asignamos el valor leído por pantalla a la variable inputNum
			
			//podriamos haber utilizado funciones para evitar errores de asignación en el caso que el usuario 
			//introdujera caracteres no numéricos, o números decimales para evitar errores en la logica o de ejecución
			//pero dado que son temas que no se han dado en la unidad, no me parecía correcto usarlos.
			
			//Evaluamos con un condicional si el número introducido cumple con los requisitos exigidos,
			//en caso afirmativo la variable numeroCorrecto cambiará a verdadero y el bucle no se repetirá
			//en caso contrario la variable seguirá Falso, y el bucle repetirá la petición
			
			si inputNum < 0 o inputNum > 99999
				Escribir "El número introducido no es correcto"
				Escribir ""
			SiNo
				numeroCorrecto <- Verdadero
			FinSi
			
		Hasta Que numeroCorrecto = Verdadero 
		
		//Inicializamos un bucle mientras que sirva para evaluar la condición de la variable numeroDeDigitosCorrecto 
		//que terminará cuando se cumplan los requisitos y esta tome el valor verdadero
		
		//Asignamos a cocienteInputNum el valor de inputNum para poder operar con el valor de inputNum sin perder el dato original
		cocienteInputNum <- inputNum 
		
		
		Mientras numeroDeDigitosCorrecto = Falso Hacer
			//vamos diviendo el valor de cocienteInputNum y los asignamos a si mismo para quedarnos con la parte con un digito menos
			//así que el número se irá haciendo más corto en cada iteración del bucle hasta que no cumpla la condición 
			
			cocienteInputNum <- cocienteInputNum / 10 
			
			//Vamos acumulando el número de iteraciones en la variable numeroDeDigitos asignada a si misma
			//y sumándole uno en cada vuelta. 
			
			numeroDeDigitos <- numeroDeDigitos + 1
			
			//Evaluamos la condición de fin de bucle cuando sólo quede un solo dígito poniendo la variable numeroDeDigitosCorrecto en verdadero
			//y por lo tanto haciendo que la condición del bucle sea Falsa y este no se ejecute más
			
			Si cocienteInputNum < 1
				numeroDeDigitosCorrecto <- Verdadero
			FinSi
			
		FinMientras
		
		//Toda la lógica de cálculo de dígitos podría ser más eficiente utilizando funciones logarítmicas, pero en la unidad 1 no hemos cubierto la parte de funciones 
		//y no nos parecía correcto usarlas para resolverlo por este método.
		
		Escribir "El número introducido tiene ", numeroDeDigitos, " cifras" //Escribe el resultado de los digitos entre dos cadenas de caracteres
		Escribir "Quiere usted continuar? Pulse s para continuar, o cualquier tecla para finalizar: "
		Leer letraParaFinalizar
		
		//Comprobamos si la cadena de caracteres introducido cumple los requisitos para finalizar el programa o continuar
		//podríamos haber utilizado la condición SiNo para redundar las dos opciones, pero ya controlamos el 
		//estado de las variables lógicas en su inicialización y condición de bucle, para ahorrarnos esas líneas de codigo
		//y hacer el Algoritmo más eficiente
		//al igual que podríamos haber utilizado un bucle for pero la lógica sería más lenta y menos intuitiva que con while
		
		Si letraParaFinalizar <> "s"
			finalizarPrograma <- Verdadero
		FinSi
	Hasta Que finalizarPrograma = Verdadero
FinAlgoritmo

