package es.cesur.dam.psp.ud5.inseguro;


// BufferedReader e InputStreamReader: Los combinamos para leer líneas completas de texto recibidas del servidor.
import java.io.BufferedReader;
import java.io.InputStreamReader;
// PrintStream: Nos permite enviar nuestra fecha al servidor como si estuviéramos imprimiendo por consola.
import java.io.PrintStream;
// Socket: Permite establecer la conexión TCP/IP hacia la IP y puerto del servidor.
import java.net.Socket;
// Scanner: Para capturar lo que el usuario teclea en su pantalla.
import java.util.Scanner;

public class TimeClient {

    public static void main(String[] args) {
        final String HOST = "localhost"; // Nos conectamos a nuestra propia máquina para la prueba
        final int PUERTO = 10000;

        System.out.println("--- CLIENTE DE CALENDARIO INICIADO ---");

        // Usamos la estructura "try-with-resources".
        // Esto es una buena práctica porque asegura que el Socket, el Scanner y los flujos 
        // se cierren automáticamente al terminar, evitando fugas de memoria, incluso si hay un fallo.
        try (
            Socket socket = new Socket(HOST, PUERTO); // Lanzamos la petición de conexión
            Scanner teclado = new Scanner(System.in);
            PrintStream salidaServidor = new PrintStream(socket.getOutputStream());
            BufferedReader entradaServidor = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            System.out.println("[+] Conexión establecida con el servidor en " + HOST + ":" + PUERTO);
            
            // Solicitamos al usuario la fecha en el formato que espera el servidor
            System.out.print("Introduce una fecha (Formato: DD MM AAAA separados por espacio): ");
            String fechaEntrada = teclado.nextLine();

            // Enviamos el texto capturado directamente al servidor a través del PrintStream
            salidaServidor.println(fechaEntrada);

            // Quedamos bloqueados esperando a que el servidor haga el cálculo y nos responda
            String respuesta = entradaServidor.readLine();
            System.out.println("[RESPUESTA DEL SERVIDOR] -> " + respuesta);

        } catch (Exception e) {
            System.err.println("[-] Error crítico de conexión: " + e.getMessage());
        }
    }
}