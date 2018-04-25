/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Message.RecipientType;
/**
 *
 * @author Wassim
 */
public class MailingService {
    
    

    public static void send(String destination, String subject, String body) {
             try {

            String host = "smtp.gmail.com";
            String user = "allforkids.fromscratch@gmail.com";
            String pass = "from_scratch";
            String From = "allForKids";
            boolean sessionDebug = false;

            Properties props = System.getProperties();

            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.required", "true");

            java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            Session mailSession = Session.getDefaultInstance(props, null);
            mailSession.setDebug(sessionDebug);
            Message msg = new MimeMessage(mailSession);
            msg.setFrom(new InternetAddress(From));
            msg.setRecipient(RecipientType.TO, new InternetAddress(destination));
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            msg.setText(body);
            Transport transport = mailSession.getTransport("smtp");
            transport.connect(host, user, pass);

            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
            System.out.println("message send successfully");
        } catch (Exception ex) {
            
        }
        }

}
