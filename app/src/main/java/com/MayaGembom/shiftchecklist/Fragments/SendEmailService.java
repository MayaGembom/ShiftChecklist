package com.MayaGembom.shiftchecklist.Fragments;

import android.content.Context;

import com.MayaGembom.shiftchecklist.More.Constants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmailService {

    private static SendEmailService instance = null;
    private static Context ctx;


    static final ExecutorService emailExecutor = Executors.newSingleThreadExecutor();

    Properties prop;
    Session session;
    private SendEmailService(Context context) {
        ctx = context;

        prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", 587);
        prop.put("mail.smtp.starttls.enable", true);
        prop.put("mail.transport.protocol", "smtp");

        session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(Constants.USERNAME, Constants.PASS);
                    }
                });
    }

    public static synchronized SendEmailService getInstance(Context context) {
        if(instance == null) {
            instance = new SendEmailService(context);
        }
        return instance;
    }

    public void SendEmail() {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(Constants.USERNAME));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("shiftchecklistapp@gmail.com")
            );
            message.setSubject("דו\"ח סיום משמרת " +new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
            message.setText("היי מנהל יקר, להלן דיווח העובדים");

            Transport.send(message);

        }
        catch (MessagingException e) {
            e.printStackTrace();
        }

    }

}
