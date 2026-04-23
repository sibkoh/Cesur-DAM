package es.cesur.dam.psp.ud5.seguro;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

// --- IMPORTS ADICIONALES PARA SEGURIDAD ---
// SSLServerSocketFactory: Es una "fábrica" especializada que crea ServerSockets pero con 
// cifrado SSL/TLS integrado. Extiende la funcionalidad de los sockets normales.
import javax.net.ssl.SSLServerSocketFactory;

public class SecureTimeServer {

    private static final String[] nombreDias = {"Domingo", "Lunes", "Martes", 
                                                "Miércoles", "Jueves", "Viernes", "Sábado"};

    public static void main(String[] args) {
        
        // --- CONFIGURACIÓN CRÍTICA DE SEGURIDAD (JSSE) ---
        // LÍNEAS CLAVE: Inyectamos en las propiedades del sistema la ubicación de nuestro Keystore.
        // El Keystore es el "anillo de claves" donde guardamos la clave privada y el certificado 
        // público que generamos con la herramienta keytool. Sin esto, el servidor no puede cifrar.
        System.setProperty("javax.net.ssl.keyStore", "SSL/serverKey.jks");
        System.setProperty("javax.net.ssl.keyStorePassword", "123456"); // Contraseña asignada al crear el archivo

        System.out.println("--- SERVIDOR SEGURO (SSL/TLS) INICIADO EN PUERTO 10000 ---");

        try {
            // Obtenemos la factoría de sockets seguros por defecto configurada con nuestro Keystore
            SSLServerSocketFactory factoriaSSL = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            
            // Creamos un ServerSocket, pero gracias a la factoría, ahora es un SSLServerSocket cifrado
            ServerSocket srvSocketSSL = factoriaSSL.createServerSocket(10000);

            while (true) {
                // Aceptamos la conexión. En este preciso instante se produce el "Handshake" (apretón de manos)
                // donde cliente y servidor negocian los algoritmos de cifrado.
                Socket cliSocket = srvSocketSSL.accept();
                System.out.println("[*] Cliente seguro conectado y verificado desde: " + cliSocket.getInetAddress());

                Scanner in = new Scanner(cliSocket.getInputStream());
                PrintStream out = new PrintStream(cliSocket.getOutputStream());
                
                int[] data = new int[3];
                boolean formatoCorrecto = true;
                
                // Mantenemos la misma lógica de lectura para garantizar la compatibilidad
                for (int i = 0; i < data.length; i++) {
                    if (in.hasNextInt()) {
                        data[i] = in.nextInt();
                    } else {
                        formatoCorrecto = false;
                        break; 
                    }
                }

                if (formatoCorrecto) {
                    data[1] -= 1; // Ajuste del mes (de 0 a 11)
                    GregorianCalendar cal = new GregorianCalendar(data[2], data[1], data[0]);
                    int dia = cal.get(Calendar.DAY_OF_WEEK) - 1;
                    
                    // Enviamos la respuesta, pero ahora viaja por la red convertida en texto ininteligible
                    out.println("Este dia es " + nombreDias[dia] + ". (Conexión verificada y cifrada)");
                } else {
                    out.println("Error: Formato de datos incorrecto o corrompido.");
                }
                
                cliSocket.close();
            }
        } catch (Exception ex) {
            System.err.println("[-] Error crítico en la capa de seguridad. Verifique el Keystore: " + ex);
        }
    }
}