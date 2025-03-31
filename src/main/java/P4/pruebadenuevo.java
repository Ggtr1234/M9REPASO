package P4;

import javax.mail.*;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class pruebadenuevo {
    public  void run() {
        Properties properties = new Properties();
        properties.put("mail.imap.host", "imap.gmail.com");
        properties.put("mail.imap.port", "993");
        properties.put("mail.imap.auth", "true");
        properties.put("mail.imap.ssl.enable", "true");
        properties.put("mail.store.protocol", "imaps");

        try {
            Session session = Session.getInstance(properties);
            Store store = session.getStore("imaps");

            InputStream senderAccountPropertiesFile = this.getClass().getClassLoader()
                    .getResourceAsStream("auth.properties");

            Properties senderAccountProperties = new Properties();
            senderAccountProperties.load(senderAccountPropertiesFile);
            System.out.println(senderAccountProperties);

            store.connect("imap.gmail.com",993,senderAccountProperties.getProperty("mail.user.account"),senderAccountProperties.getProperty( "mail.app.password"));

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            Message[] messages = inbox.getMessages();
            for (Message message : messages) {
                System.out.println("De: " + InternetAddress.toString(message.getFrom()));
                System.out.println("Per a: " + InternetAddress.toString(message.getRecipients(Message.RecipientType.TO)));
                System.out.println("Data: " + message.getSentDate());
                System.out.println("Assumpte: " + message.getSubject());
                System.out.println("Contingut: " + message.getContent());
                System.out.println("Mime: " + llergirContent(message));
                System.out.println("--------------------------------------------");
            }



            inbox.close(false);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private String llergirContent(Message message){
        Object content = null;
        try {
            content = message.getContent();
            if (content instanceof String) {
                System.out.println("Contingut: " + content);
            } else if (content instanceof MimeMultipart) {
                MimeMultipart multipart = (MimeMultipart) content;
                for (int i = 0; i < multipart.getCount(); i++) {
                    BodyPart part = multipart.getBodyPart(i);
                    System.out.println("Part " + i + ": " + part.getContent());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        return "";
    }
}
