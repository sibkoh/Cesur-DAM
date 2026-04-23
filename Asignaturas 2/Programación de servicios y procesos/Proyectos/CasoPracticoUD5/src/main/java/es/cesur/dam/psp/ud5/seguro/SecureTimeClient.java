package es.cesur.dam.psp.ud5.seguro;

// --- IMPORTS ADICIONALES PARA SEGURIDAD ---
// SSLSocketFactory: La "fábrica" del lado del cliente para crear túneles cifrados SSL.
import javax.net.ssl.SSLSocketFactory;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Scanner;

public class SecureTimeClient {

    public static void main(String[] args) {
        
        // --- CONFIGURACIÓN CRÍTICA DE SEGURIDAD (JSSE) ---
        // LÍNEAS CLAVE: El cliente no necesita un Keystore propio (autenticación simple), 
        // pero SÍ necesita un TrustStore (Almacén de confianza). 
        // Aquí le decimos a Java: "Confía únicamente en los servidores cuyo certificado público esté dentro de este archivo".
        System.setProperty("javax.net.ssl.trustStore", "SSL/clientTrust.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "123456");

        final String HOST = "localhost";
        final int PUERTO = 10000;

        System.out.println("--- CLIENTE SEGURO (SSL/TLS) ---");

        try {
            // Instanciamos la factoría de sockets que ya ha cargado nuestro TrustStore en memoria
            SSLSocketFactory factoriaSSL = (SSLSocketFactory) SSLSocketFactory.getDefault();
            
            // Reutilizamos el bloque try-with-resources para mantener un código limpio y eficiente
            try (
                // Creamos un socket seguro hacia el servidor. Si el certificado del servidor 
                // no está en nuestro TrustStore, esta línea lanzará una excepción y abortará.
                Socket socketSeguro = factoriaSSL.createSocket(HOST, PUERTO);
                Scanner teclado = new Scanner(System.in);
                PrintStream salida = new PrintStream(socketSeguro.getOutputStream());
                BufferedReader entrada = new BufferedReader(new InputStreamReader(socketSeguro.getInputStream()))
            ) {
                System.out.println("[+] Handshake SSL completado con éxito. Enlace blindado.");
                
                System.out.print("Introduce una fecha a consultar (DD MM AAAA): ");
                String peticion = teclado.nextLine();

                // Toda esta información viaja ahora cifrada, protegiendo los datos simulados de la farmacia
                salida.println(peticion);

                String respuesta = entrada.readLine();
                System.out.println("[RESPUESTA CIFRADA DEL SERVIDOR] -> " + respuesta);
            }
            
        } catch (Exception e) {
            // Manejo de errores específico. Si falla aquí, suele ser problema de rutas de los archivos JKS.
            System.err.println("[-] Excepción de seguridad o conexión: " + e.getMessage());
            System.err.println("Consejo: Verifique que la ruta de la carpeta 'SSL' es correcta.");
        }
    }
}