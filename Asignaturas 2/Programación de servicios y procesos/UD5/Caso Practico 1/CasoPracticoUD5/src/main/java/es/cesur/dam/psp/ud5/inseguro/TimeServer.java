package es.cesur.dam.psp.ud5.inseguro;


// PrintStream: Lo usamos para enviar flujos de texto (la respuesta) al cliente de forma cómoda.
import java.io.PrintStream;
// ServerSocket: Es la clase fundamental para crear un servidor que escuche peticiones entrantes.
import java.net.ServerSocket;
// Socket: Representa la conexión individual o "túnel" temporal con el cliente que se acaba de conectar.
import java.net.Socket;
// Calendar y GregorianCalendar: Clases nativas de Java para manipular fechas y realizar cálculos con el calendario.
import java.util.Calendar;
import java.util.GregorianCalendar;
// Scanner: Lo utilizamos aquí para parsear (leer y dividir) los datos de entrada del cliente.
import java.util.Scanner;

public class TimeServer {

    // Definimos un array constante con los nombres de los días para traducir el número que nos devuelva el calendario a texto.
    private static final String[] nombreDias = {"Domingo", "Lunes", "Martes", 
                                                "Miércoles", "Jueves", "Viernes", "Sábado"};

    public static void main(String[] argv) throws Exception {
        try {
            // Instanciamos el servidor en el puerto 10000. Aquí es donde nos quedaremos "escuchando".
            ServerSocket srvSocket = new ServerSocket(10000);
            System.out.println("Servidor TimeServer (INSEGURO) a la escucha en el puerto 10000...");

            // Bucle infinito: el servidor nunca se apaga, siempre espera al siguiente cliente.
            while(true) {
                // Esta es una línea bloqueante. El hilo se detiene aquí hasta que un cliente llama a la puerta.
                Socket cliSocket = srvSocket.accept();

                // Capturamos el flujo de entrada del cliente para leer lo que nos ha enviado.
                Scanner in = new Scanner(cliSocket.getInputStream());
                
                // Preparamos un array de 3 posiciones para guardar: [0] Día, [1] Mes, [2] Año.
                int[] data = new int[3];
                boolean ok = true; // Bandera para controlar si el formato recibido es válido.

                // Intentamos leer exactamente 3 números enteros separados por espacios.
                for(int i = 0; i < data.length; i++) {
                    if(in.hasNextInt()) {
                        data[i]= in.nextInt(); // Guardamos el número si existe
                    } else {
                        ok = false; // Si manda una letra o faltan datos, marcamos error
                        break; // Decidimos salir del bucle para no consumir recursos innecesarios
                    }          
                }
                
                // Preparamos el canal de salida para contestarle al cliente.
                PrintStream out = new PrintStream(cliSocket.getOutputStream());        
                
                if (ok) {
                    // LÍNEA CLAVE: Restamos 1 al mes. ¿Por qué? Porque la clase GregorianCalendar 
                    // indexa los meses desde 0 (Enero es 0, Febrero es 1... Diciembre es 11).
                    data[1] -= 1;
                    
                    // Creamos el objeto calendario pasándole Año, Mes (ajustado) y Día.
                    GregorianCalendar cal = new GregorianCalendar(data[2], data[1], data[0]);
                    
                    // Obtenemos el día de la semana. Calendar.DAY_OF_WEEK devuelve 1 para Domingo, 2 para Lunes...
                    // Le restamos 1 para que coincida exactamente con los índices de nuestro array 'nombreDias' (0 a 6).
                    int dia = cal.get(Calendar.DAY_OF_WEEK) - 1;
                    
                    // Enviamos la respuesta final al cliente.
                    out.println("Este dia es " + nombreDias[dia] + ".");
                    
                } else {
                    out.println("Formato de datos incorrecto.");
                }
                
                // Cerramos la conexión con este cliente en particular. El servidor seguirá vivo para el siguiente.
                cliSocket.close();
            }
        } catch(Exception ex) {
            System.out.println("Error en la comunicacion: " + ex);
        }
    }
}