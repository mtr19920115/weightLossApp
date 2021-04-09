package com.ming.weightlossapp.TechnicalServices.JavaMail;

import java.util.Date;
import java.util.Properties;


import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class JavaMail {
    public JavaMail(){

    }

    public static void sendMail(String subject, String content, String email) throws MessagingException, Exception
    {
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtps");
        props.setProperty("mail.smtps.host", "smtp.gmail.com");
        props.setProperty("mail.smtps.auth", "true");
        final String smtpPort = "465";
        props.setProperty("mail.smtps.port", smtpPort);
        props.setProperty("mail.smtps.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtps.socketFactory.fallback", "false");
        props.setProperty("mail.smtps.socketFactory.port", smtpPort);
        Session session = Session.getInstance(props);
        session.setDebug(true);
        MimeMessage message = createMimeMessage(session, "weightlossappcpsc597@gmail.com", email, subject, content);
        Transport transport = session.getTransport();
        transport.connect("weightlossappcpsc597@gmail.com", "nwsama520");
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }

    public static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail, String subject, String content) throws Exception
    {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(sendMail, "WeightLossApp", "UTF-8"));
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "", "UTF-8"));
        message.setSubject(subject, "UTF-8");
        MimeBodyPart text = new MimeBodyPart();
        text.setContent(content, "text/html;charset=UTF-8");
        MimeMultipart mailBody= new MimeMultipart();
        mailBody.addBodyPart(text);
        mailBody.setSubType("related");
        message.setContent(mailBody);
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
    }
}
