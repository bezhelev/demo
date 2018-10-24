package com.example.demo.logiclvl;

import com.example.demo.dao.DAOContactInterface;
import com.example.demo.dao.DAOContacts;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailSender {

    private List<String> userList = new ArrayList<>();
    private String messageSubject = new String();
    private String messageText = new String();
    private final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }

    public EmailSender(List<String> strings) {
        for(String st:strings) System.out.println(st);
        messageSubject = strings.get(strings.size()-2);
        messageText = strings.get(strings.size()-1);
        for(int i=0;i<strings.size()-2;i++){
            if(validate(strings.get(i))) userList.add(strings.get(i));
        }
    }

    public void sendEmailToUser(){
        for(String email:userList){
            sendEmailMethod(email,messageSubject,messageText);
        }
    }

    public void callAutomaticSending(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 53);
        calendar.set(Calendar.SECOND, 20);
        Date alarmTime = calendar.getTime();
        Timer timer = new Timer();
        timer.schedule(new AutomaticSending(), alarmTime);
    }

    private class AutomaticSending extends TimerTask{
        public void run() {
            DAOContactInterface contactDAO =  new DAOContacts();
            for(String email:(List<String>)contactDAO.getBirthdayBoyEmail()){
                if(validate(email))
                    sendEmailMethod(email,"Birthday","Happy BIRTHDAY!");
            }
        }
    }

    public EmailSender() {
    }

    private void sendEmailMethod(String toWhom, String messageSubject, String messageText){

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("asd", "asdasd");
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("bezhelev98@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(toWhom));
            message.setSubject(messageSubject);
            message.setText(messageText);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }




    public List<String> getUserList() {
        return userList;
    }

    public void setUserList(List<String> userList) {
        this.userList = userList;
    }

    public String getMessageSubject() {
        return messageSubject;
    }

    public void setMessageSubject(String messageSubject) {
        this.messageSubject = messageSubject;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
}
