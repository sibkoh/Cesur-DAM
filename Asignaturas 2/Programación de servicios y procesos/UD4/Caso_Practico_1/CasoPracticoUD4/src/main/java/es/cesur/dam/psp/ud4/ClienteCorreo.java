package es.cesur.dam.psp.ud4;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * En esta clase encapsulamos toda la lógica necesaria para conectarnos a un servidor SMTP.
 * Hemos implementado soporte tanto para SSL como para TLS, siguiendo los requisitos del enunciado.
 */
public class ClienteCorreo {

    // Definimos las propiedades básicas para la conexión (Host y Puerto)
    private String smtpHost;
    private int smtpPort;
    
    // Aquí almacenaremos los datos del correo antes de enviarlo
    private String senderEmail;
    private String subject;
    private String mailBody;
    
    // Usamos una lista para permitir múltiples destinatarios, aunque en el ejemplo usemos uno
    private List<String> recipientsList;

    /**
     * Constructor: Recibimos los datos del servidor al instanciar la clase.
     * Inicializamos la lista de destinatarios para evitar errores de tipo NullPointerException.
     */
    public ClienteCorreo(String host, int port) {
        this.smtpHost = host;
        this.smtpPort = port;
        this.recipientsList = new ArrayList<>();
    }

    // --- MÉTODOS DE CONFIGURACIÓN (Setters personalizados) ---

    // Añadimos un array de destinatarios a nuestra lista interna
    public void addRecipients(String[] recipients) {
        if (recipients != null) {
            for (String r : recipients) {
                this.recipientsList.add(r);
            }
        }
    }

    // Método para añadir un solo destinatario
    public void addRecipient(String recipient) {
        if (recipient != null && !recipient.isEmpty()) {
            this.recipientsList.add(recipient);
        }
    }

    // Asignamos el remitente del correo
    public void setSender(String psender) {
        this.senderEmail = psender;
    }

    // Asignamos el asunto
    public void setSubject(String subject) {
        this.subject = subject;
    }

    // Asignamos el cuerpo del mensaje (texto plano)
    public void setMailText(String pbody) {
        this.mailBody = pbody;
    }

    // --- MÉTODOS DE ENVÍO (Lógica Core) ---

    /**
     * Método para enviar usando SSL (Secure Sockets Layer).
     * Configuramos las propiedades específicas que requiere JavaMail para cifrar la conexión desde el inicio.
     */
    public void sendUsingSSLAuthentication(final String user, final String pass) throws MessagingException {
        // 1. Configuramos las Properties del sistema
        Properties props = new Properties();
        props.put("mail.smtp.host", this.smtpHost);
        
        // Configuración específica para SSL: indicamos la clase SocketFactory
        props.put("mail.smtp.socketFactory.port", String.valueOf(this.smtpPort));
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        
        props.put("mail.smtp.auth", "true"); // Indicamos que requerimos autenticación
        props.put("mail.smtp.port", String.valueOf(this.smtpPort));

        // 2. Creamos la sesión. Usamos una clase anónima para pasar las credenciales (Authenticator)
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });

        // 3. Construimos el mensaje y lo enviamos
        Message message = buildMessage(session);
        Transport.send(message);
        
        // Feedback por consola para verificar el envío
        System.out.println("LOG: Correo enviado exitosamente vía SSL (Puerto " + this.smtpPort + ").");
    }

    /**
     * Método para enviar usando TLS (Transport Layer Security).
     * A diferencia de SSL, aquí activamos 'starttls' para elevar la seguridad de la conexión.
     */
    public void sendUsingTLSAuthentication(final String user, final String pass) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        
        // Esta es la línea clave para TLS: STARTTLS
        props.put("mail.smtp.starttls.enable", "true"); 
        
        props.put("mail.smtp.host", this.smtpHost);
        props.put("mail.smtp.port", String.valueOf(this.smtpPort));

        // Obtenemos la sesión igual que en el método anterior
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });

        Message message = buildMessage(session);
        Transport.send(message);
        System.out.println("LOG: Correo enviado exitosamente vía TLS (Puerto " + this.smtpPort + ").");
    }

    /**
     * Método para enviar sin autenticación.
     * Nota: Implementado según requisitos, aunque la mayoría de servidores públicos (Gmail) lo rechazarán.
     */
    public void send() throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", this.smtpHost);
        props.put("mail.smtp.port", String.valueOf(this.smtpPort));
        // No configuramos auth ni starttls aquí

        Session session = Session.getInstance(props);
        Message message = buildMessage(session);
        
        Transport.send(message);
        System.out.println("LOG: Correo enviado sin autenticación.");
    }

    /**
     * Método auxiliar privado (Helper).
     * Lo hemos creado para no repetir el código de construcción del mensaje MIME en cada método de envío.
     */
    private Message buildMessage(Session session) throws MessagingException {
        // Validamos que tenemos los datos mínimos necesarios antes de intentar construir el mensaje
        if (this.senderEmail == null || this.recipientsList.isEmpty()) {
            throw new MessagingException("Error: Faltan datos obligatorios (Remitente o Destinatarios).");
        }

        // Creamos el objeto MimeMessage estándar
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(this.senderEmail));
        
        // Añadimos todos los destinatarios de la lista
        for (String recipient : this.recipientsList) {
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        }
        
        message.setSubject(this.subject);
        message.setText(this.mailBody);
        
        return message;
    }
}