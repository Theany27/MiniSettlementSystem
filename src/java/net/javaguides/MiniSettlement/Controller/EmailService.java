/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.javaguides.MiniSettlement.Controller;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
/**
 *
 * @author THEANY
 */
public class EmailService {

    public static void sendFirstPassword(String username, String password, String role) {

        final String fromEmail = "theany8080@gmail.com";
        final String appPassword = "w u f l s q z p b l b d m q w z";
        String toEmail= "rotheanykhoeurn@gmail.com";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, appPassword);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(toEmail));

            message.setSubject("Your First Login Password");

            message.setText("Welcome!\n\n  Dear username: "+username+"\n Your temporary password is: " + password + "\n and Your role is: "+ role
                    + "\nPlease change it!!!.");

            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
