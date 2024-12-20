package services;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.mail.*;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class EmailService {

    private static final String email;
    private static final String password;
    private Session session;

    static {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream("/Users/perepalacin/Documents/pere-repos/files-system/sos-etseib/env.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read email configuration variables", e);
        }
        email = props.getProperty("EMAIL").trim();
        password = props.getProperty("GMAIL_APP_PASSWORD").trim();
    }

    public EmailService() {
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        this.session = Session.getInstance(prop, new jakarta.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });
    }

    public void sendVerificationEmail(String toEmail, String username, String validationUrl) throws AddressException, MessagingException {
        System.out.println("Building email!");
        Message message = new MimeMessage(this.session);
        message.setFrom(new InternetAddress(email));
        message.setHeader("Auto-Submitted", "auto-generated");
        System.out.println("Sending from: " + email);
        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(toEmail)
        );
        System.out.println("Sending to: " + toEmail);

        message.setSubject("Valida la teva direcció de correu a SOS - ETSEIB");
        String htmlContent = "<div><h1>" + username + ", benvingut a SOS - ETSEIB</h1><p>Ja casi hem acabat amb el procés de registre. Accedeix a l'enllaç a continuació per validar la teva direcció de correu: <a href=" + validationUrl + ">Valida el teu correu</a></p></div>";
        message.setContent(htmlContent, "text/html");
        Transport.send(message);
        System.out.println("Mail sent!");
    }

}
