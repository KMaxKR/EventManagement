package com.ks.EventManagement.service;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;


@Component
public class EmailService {

    @Value("${spring.mail.username}")
    private String from;

    @Value("${spring.mail.password}")
    private String password;

    public void sendEmail(String to, String subject, String text){
        Authenticator authenticator = new Authenticator() {
            private PasswordAuthentication authentication;
            {
                authentication = new PasswordAuthentication(from, password);
            }

            protected PasswordAuthentication getPasswordAuthentication() {
                return authentication;
            }
        };

        Properties properties = new Properties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(properties);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(from);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(text);
            Transport.send(message);
            System.out.println("Message sent");
        }catch (MessagingException e){
            System.out.println("Smth went wrong: " + e.toString());
        }finally {
            System.out.println("Was called function sendEmail");
        }
    }
}
