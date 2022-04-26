package com.example.CyProject.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;

    public String find_upw_sendEmail(String email){
        Random random = new Random();
        String emailKey = "";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        for(int i=0; i<4; i++){
            String randomKey = String.valueOf(random.nextInt(10));
            emailKey += randomKey;
        }

        message.setSubject("비밀번호 찾기 인증 번호");
        message.setText(emailKey);
        javaMailSender.send(message);
        System.out.println(message + "service");
        return emailKey;

    }
}


