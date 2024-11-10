Algoritmo Ejercicio4
	
    // Variables
    Definir peso, altura, imc Como Real
    Definir niveDePeso Como Cadena
    Definir pesoNoCorrecto, alturaNoCorrecta Como Booleano
	
    peso <- 0
    altura <- 0
    imc <- 0
    pesoNoCorrecto <- Verdadero
    alturaNoCorrecta <- Verdadero
    niveDePeso <- ""
	
    // Bucle para verificar el peso
    Mientras pesoNoCorrecto Hacer
        Escribir "\nIntroduce tu peso, si tiene decimales usa coma, no punto (El peso debe ser igual o mayor a 30 kg y menor o igual a 300 kg): "
        Leer peso
		
        Si peso < 30 O peso > 300 Entonces
            Escribir "\nError. Vuelve a introducir un peso válido"
        Sino
            pesoNoCorrecto <- Falso
            Escribir "\npeso valido"
            
            // Bucle para verificar la altura
            Mientras alturaNoCorrecta Hacer
                Escribir "\nIntroduce tu altura, si tiene decimales usa coma, no punto (La altura debe estar comprendida entre 1.30 metros y 2 metros): "
                Leer altura
				
                Si altura < 1.30 O altura > 2.00 Entonces
                    Escribir "\nError. Vuelve a introducir una altura válida"
                Sino
                    alturaNoCorrecta <- Falso
                    Escribir "\naltura valida"
                FinSi
            FinMientras
        FinSi
    FinMientras
	
    // Calcular el IMC
    imc <- peso / (altura * altura)
	
    // Determinar el nivel de peso basado en el IMC
    Si imc < 18.5 Entonces
        niveDePeso <- "Bajo peso"
    Sino
        Si imc >= 18.5 Y imc <= 24.9 Entonces
            niveDePeso <- "Normal"
        Sino
            Si imc >= 25 Y imc <= 29.9 Entonces
                niveDePeso <- "Sobrepeso"
            Sino
                niveDePeso <- "Obesidad"
            FinSi
        FinSi
    FinSi
	
    // Mostrar el resultado
    Escribir "\nTu IMC es de : ", imc, " lo que corresponde a un nivel de peso de: ", niveDePeso
	
FinAlgoritmo
