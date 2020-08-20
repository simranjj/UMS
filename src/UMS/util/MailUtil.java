package UMS.util;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class MailUtil {
    public static void sendMail(String receiver, String msg) throws Exception {
        Properties properties = new Properties();
        properties.put("mail.smtp.port", 587);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        String fromUser = "javatest35@gmail.com";
        String fromUserEmailPassword = "javahere26@";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromUser, fromUserEmailPassword);
            }
        });
        Message message = prepareMessage(session, fromUser, receiver, msg);
        Transport.send(message);
    }

    private static Message prepareMessage(Session session, String sender, String receiver, String msg) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
            message.setSubject("PILLAR SECURITY");
            Multipart multipart = new MimeMultipart();
            BodyPart bodyPart = new MimeBodyPart();
            bodyPart.setText(msg);
            multipart.addBodyPart(bodyPart);
            bodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource("D:\\UMS\\UMS\\src\\UMS\\assets\\Report.pdf");
            bodyPart.setDataHandler(new DataHandler(source));
            bodyPart.setFileName("Report");
            multipart.addBodyPart(bodyPart);
            message.setContent(multipart);
            return message;
        } catch (Exception ex) {
            System.out.println(ex + "s");
        }
        return null;
    }
}
