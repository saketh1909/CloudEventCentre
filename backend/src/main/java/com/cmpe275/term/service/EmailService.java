package com.cmpe275.term.service;

import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
@Service
public class EmailService {

    public void sendMailToRecipients(String name,String email,String message,String subject) throws AddressException, MessagingException, IOException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("CloudEventCenter@gmail.com", "Cmpe275@CEC");
            }
        });
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("CloudEventCenter@gmail.com", false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
        String messageContent=message;
        msg.setSubject(subject);
        msg.setContent(messageContent, "text/html");
        msg.setSentDate(new Date());
        Transport.send(msg);
    }
}
