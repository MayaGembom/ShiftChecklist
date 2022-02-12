package com.MayaGembom.shiftchecklist.More;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.MayaGembom.shiftchecklist.Objects.Assignment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    public static final ExecutorService emailExecutor = Executors.newSingleThreadExecutor();

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

    public void SendEmail(ArrayList<Assignment> assignments) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(Constants.USERNAME));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("shiftchecklistapp@gmail.com")
            );
            message.setSubject("דו\"ח סיום משמרת " +new SimpleDateFormat("dd-MM-yyyy").format(new Date()));


            StringBuilder msgToBodyMail = new StringBuilder();
            for (int i = 0 ; i < assignments.size() ; i ++)
            {
                Log.d("ptttt", "SendEmail: " +assignments.get(i));
                msgToBodyMail.append(String.valueOf("שם המשימה: " + assignments.get(i).getTitle() + "\n"));
                if(assignments.get(i).getStatus().equals("complete"))
                    msgToBodyMail.append(String.valueOf("סטאטוס: בוצע " + "\n"));
                if(assignments.get(i).getStatus().equals("incomplete"))
                    msgToBodyMail.append(String.valueOf("סטאטוס: לא בוצע " + "\n"));
                if (!TextUtils.isEmpty(assignments.get(i).getNotes()))
                    msgToBodyMail.append(String.valueOf("הערות העובד/ת: " + assignments.get(i).getNotes() + "\n"));
                msgToBodyMail.append("\n");

            }
            message.setText(msgToBodyMail.toString());
            message.saveChanges();

            Transport.send(message);

        }
        catch (MessagingException e) {
            e.printStackTrace();
        }

    }

}
