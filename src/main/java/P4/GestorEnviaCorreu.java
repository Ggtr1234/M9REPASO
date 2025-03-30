package P4;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GestorEnviaCorreu {

    private Session session;

    public GestorEnviaCorreu() throws IOException {
        Properties SMTPServerProperties = new Properties();

        InputStream SMTPServerPropertiesFile = this.getClass().getClassLoader()
                .getResourceAsStream("SMTPServer.properties");
        SMTPServerProperties.load(SMTPServerPropertiesFile);
        System.out.println(SMTPServerProperties);

        InputStream senderAccountPropertiesFile = this.getClass().getClassLoader()
                .getResourceAsStream("auth.properties");
        Properties senderAccountProperties = new Properties();
        senderAccountProperties.load(senderAccountPropertiesFile);

        this.session = Session.getDefaultInstance(SMTPServerProperties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(senderAccountProperties.getProperty("mail.user.account")
                                ,senderAccountProperties.getProperty("mail.app.password"));
                    }
                }
        );

    }

    public void enviarCorreo(String destinatario, String contenido, File[] adjunts, String titulo) {
        Message message = new MimeMessage(this.session);

        try {
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
            message.setSubject(titulo);

            if (adjunts == null) {
                message.setText(contenido);
            } else {
                Multipart multipart = new MimeMultipart();

                MimeBodyPart textPart = new MimeBodyPart();
                textPart.setText(contenido);
                multipart.addBodyPart(textPart);

                for (File attachment : adjunts) {
                    MimeBodyPart mimeBodyPart = new MimeBodyPart();
                    mimeBodyPart.attachFile(attachment);
                    multipart.addBodyPart(mimeBodyPart);
                }
                message.setContent(multipart);
            }

            Transport.send(message);
            System.out.println("Enviando Correo");
            Transport.send(message);
            System.out.println("Correo enviado com sucesso");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
