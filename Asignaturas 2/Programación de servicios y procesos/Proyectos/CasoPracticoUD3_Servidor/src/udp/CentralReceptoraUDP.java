package udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class CentralReceptoraUDP {

    public static void main(String[] args) {
    	
    	DatagramSocket socket = null; // declaramos la variable aquí para que exista fuera

        try {
            // Puerto en el que la central va a escuchar los avisos
            int puertoEscucha = 5000;

            // Creamos el socket UDP asociado a ese puerto
            socket = new DatagramSocket(puertoEscucha);

            System.out.println("Central de alarmas iniciada y a la espera de avisos...\n");

            // La central debe estar siempre activa, por eso usamos un bucle infinito
            while (true) {

                // Creamos un buffer donde se almacenará temporalmente el mensaje recibido
                byte[] buffer = new byte[1024];

                // Creamos el paquete UDP que recibirá la información
                DatagramPacket paquete = new DatagramPacket(buffer, buffer.length);

                // Este método se queda bloqueado hasta que llega un aviso
                socket.receive(paquete);

                // Convertimos los bytes recibidos en un String legible
                String mensajeRecibido = new String(
                        paquete.getData(), 0, paquete.getLength());

                // Procesamos el aviso recibido
                mostrarAviso(mensajeRecibido);
               
            }

        } catch (Exception e) {
            System.out.println("Error en la central de recepción de alarmas");
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close(); // libera el puerto
            }
        }
    }

    /**
     * Este método analiza el tipo de aviso recibido y lo muestra
     * de forma clara en la consola de la central.
     */
    private static void mostrarAviso(String mensaje) {

    	
        // Comprobamos el tipo de aviso según el prefijo del mensaje
        if (mensaje.startsWith("INFO")) {
            System.out.println("[INFORMACIÓN recibida] " + mensaje.substring(6));

        } else if (mensaje.startsWith("WARNING")) {
            System.out.println("[ADVERTENCIA recibida] " + mensaje.substring(9));

        } else if (mensaje.startsWith("ALERT")) {
            System.out.println("[ALERTA CRÍTICA recibida] " + mensaje.substring(7));

        } else {
            // Por si llega algún mensaje que no siga el formato esperado
            System.out.println("[MENSAJE DESCONOCIDO recibido] " + mensaje);
        }
    }
}
