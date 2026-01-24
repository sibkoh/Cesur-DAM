package es.cesur.dam.psp.ud4;

import java.util.Scanner;
import javax.mail.MessagingException;

/**
 * Clase principal actualizada para incluir la prueba SIN AUTENTICACIÓN.
 */
public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("=========================================");
        System.out.println("   CASO PRÁCTICO UD4 - CLIENTE CORREO    ");
        System.out.println("=========================================");

        // --- RECOGIDA DE DATOS ---
        System.out.print("Introduce tu correo Gmail completo: ");
        String miCorreo = sc.nextLine();
        
        // Si se va a probar la opción 3, la contraseña no se usará, pero la pide igual para las otras
        System.out.print("Introduce tu Contraseña de Aplicación: ");
        String miPassword = sc.nextLine();
        
        System.out.print("Introduce el correo del destinatario: ");
        String destinatario = sc.nextLine();

        System.out.println("\nSelecciona el protocolo de envío:");
        System.out.println("1. TLS (Puerto 587) - Recomendado");
        System.out.println("2. SSL (Puerto 465) - Legacy");
        System.out.println("3. Sin Autenticación (Prueba de error controlado)"); 
        System.out.print("Opción: ");
        int opcion = sc.nextInt();
        
        String host = "smtp.gmail.com";
        int port;

        // Asignamos el puerto según la opción
        switch (opcion) {
            case 1: port = 587; break; // TLS
            case 2: port = 465; break; // SSL
            case 3: port = 25;  break; // Puerto estándar sin cifrar (Gmail lo bloqueará, pero es lo correcto)
            default: port = 587; break;
        }

        // 1. INSTANCIACIÓN
        ClienteCorreo cliente = new ClienteCorreo(host, port);

        // 2. CONFIGURACIÓN DEL MENSAJE
        cliente.setSender(miCorreo);
        cliente.addRecipient(destinatario);
        cliente.setSubject("Prueba UD4 - Opción " + opcion);
        cliente.setMailText("Prueba de envío.\nFecha: " + new java.util.Date());

        // 3. ENVÍO
        System.out.println("\nConectando con " + host + " en el puerto " + port + "...");
        
        try {
            switch (opcion) {
                case 1:
                    cliente.sendUsingTLSAuthentication(miCorreo, miPassword);
                    System.out.println("✅ ÉXITO (TLS): Revisa la bandeja de entrada.");
                    break;
                case 2:
                    cliente.sendUsingSSLAuthentication(miCorreo, miPassword);
                    System.out.println("✅ ÉXITO (SSL): Revisa la bandeja de entrada.");
                    break;
                case 3:
                    // AQUÍ LLAMAMOS AL MÉTODO "HUÉRFANO"
                    System.out.println("⚠️ Intentando envío anónimo (Se espera fallo con Gmail)...");
                    cliente.send(); 
                    System.out.println("✅ ÉXITO: ¡Increíble! El servidor aceptó correo anónimo.");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
            
        } catch (MessagingException e) {
            System.err.println("❌ RESULTADO ESPERADO U ERROR:");
            // Si es la opción 3, explicamos que el error es normal
            if (opcion == 3) {
                System.out.println("Nota: Gmail ha rechazado la conexión anónima. ESTO ES CORRECTO.");
                System.out.println("Significa que tu método send() funciona y se comunica con el servidor.");
                System.out.println("Mensaje del servidor: " + e.getMessage());
            } else {
                e.printStackTrace();
            }
        } finally {
            sc.close();
        }
    }
}