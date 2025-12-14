package udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class AlarmaClienteUDP {

    public static void main(String[] args) {

        try {
            // Dirección IP del servidor.
            // En este caso usamos "localhost" porque estamos probando
            // el cliente y el servidor en el mismo ordenador.
            InetAddress direccionCentral = InetAddress.getByName("localhost");

            // Puerto por el que la central está escuchando los avisos
            int puertoCentral = 5000;

            // Creamos el socket UDP.
            // No se establece ninguna conexión previa, simplemente se crea
            // el canal por el que se enviarán los mensajes.
            DatagramSocket socket = new DatagramSocket();

            // Envío de un mensaje informativo
            enviarAviso(socket, direccionCentral, puertoCentral,
                    "INFO: Sistema de alarma activado correctamente en la vivienda");

            // Envío de un aviso de advertencia
            enviarAviso(socket, direccionCentral, puertoCentral,
                    "WARNING: Sensor de movimiento detecta actividad inusual");

            // Envío de un aviso crítico
            enviarAviso(socket, direccionCentral, puertoCentral,
                    "ALERT: Posible intrusión detectada. Avisar a las autoridades");

            // Cerramos el socket una vez enviados todos los avisos
            socket.close();

        } catch (Exception e) {
            // En caso de error mostramos un mensaje y la traza
            System.out.println("Se ha producido un error en el dispositivo de alarma");
            e.printStackTrace();
        }
    }

    /**
     * Este método se encarga de enviar un aviso concreto a la central.
     * Recibe el mensaje como texto y lo envía mediante UDP.
     */
    private static void enviarAviso(DatagramSocket socket,
                                    InetAddress direccion,
                                    int puerto,
                                    String mensaje) throws Exception {

        // Convertimos el mensaje de texto en un array de bytes,
        // ya que UDP trabaja siempre a nivel de bytes
        byte[] datos = mensaje.getBytes();

        // Creamos el paquete UDP indicando:
        // - Los datos a enviar
        // - El tamaño del mensaje
        // - La dirección IP de destino
        // - El puerto de destino
        DatagramPacket paquete = new DatagramPacket(
                datos, datos.length, direccion, puerto);

        // Enviamos el paquete a la red
        socket.send(paquete);

        // Mostramos por consola el mensaje enviado (solo para pruebas)
        System.out.println("Alarma envía aviso -> " + mensaje);
    }
}
