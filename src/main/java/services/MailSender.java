package services;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;


public class MailSender {

    public static void sendEmail(String toEmail, String subject, String body) {
        final String fromEmail = "mouadhnemri04@gmail.com"; // Your email
        final String password = "pfzvvjleaavtinwd"; // Your password

        try {
            Email email = new HtmlEmail();
            email.setHostName("smtp.gmail.com");
            email.setSmtpPort(587);
            email.setAuthenticator(new DefaultAuthenticator(fromEmail, password));
            email.setSSLOnConnect(true); // Use SSL/TLS
            email.setStartTLSEnabled(true); // Enable TLS
            email.setFrom(fromEmail);
            email.setSubject(subject);
            email.setMsg(body);
            email.addTo(toEmail);
            email.send();
            System.out.println("Email sent successfully!");
        } catch (EmailException e) {
            System.err.println("Email sending failed: " + e.getMessage());
        }
    }



}