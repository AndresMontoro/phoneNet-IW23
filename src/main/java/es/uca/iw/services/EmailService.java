package es.uca.iw.services;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailService {

    public static void sendRecoveryEmail(String toEmail, String recoveryLink) {
        final String fromEmail = "PhoneNetIW@gmail.com";
        final String password = "qfjn gpku ultj mgab";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Recuperación de Contraseña");
            message.setText("Hola,\n\nPuedes recuperar tu contraseña haciendo clic en el siguiente enlace:\n" + recoveryLink);

            Transport.send(message, fromEmail, password);
            System.out.println("Correo enviado correctamente.");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Error al enviar el correo de recuperación.");
        }
    }
}