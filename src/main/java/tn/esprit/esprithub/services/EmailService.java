package tn.esprit.esprithub.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import tn.esprit.esprithub.mailRequest.MailRequest;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender emailSender;

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

//    public void sendReservationReminder(String to, String reservationDate) {
//
//        String subject = "Reservation Reminder";
//        String text = "This is a reminder for your reservation scheduled for " + reservationDate;
//
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(to);
//        message.setSubject(subject);
//        message.setText(text);
//        emailSender.send(message);
//    }
public void sendReservationReminder(MailRequest mailRequest) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(mailRequest.getTo());
    message.setSubject(mailRequest.getSubject());
    message.setText(mailRequest.getText());
    emailSender.send(message);
}
}
