package com.booklovers.booklovers.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOtpEmail(String email, String otp) {

        SimpleMailMessage message =
                new SimpleMailMessage();

        message.setTo(email);
        message.setSubject("Password Reset OTP");

        message.setText(
                "Hello,\n\n" +
                "Your OTP is: " + otp +
                "\n\nThis OTP expires in 5 minutes.\n\n" +
                "If you did not request this reset, ignore this email."
        );

        mailSender.send(message);
    }
}
