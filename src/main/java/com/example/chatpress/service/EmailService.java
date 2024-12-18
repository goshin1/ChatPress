package com.example.chatpress.service;

import com.example.chatpress.entity.UserEntity;
import com.example.chatpress.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Autowired
    private UserRepository userRepository;

    private final JavaMailSender javaMailSender;
    private static int number;

    public static void createNumber(){
        number = (int)(Math.random() * (90000)) + 100000;// (int) Math.random() * (최댓값-최소값+1) + 최소값
    }

    public MimeMessage CreateMail(String mail){
        createNumber();
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(mail);
            message.setRecipients(MimeMessage.RecipientType.TO, mail);
<<<<<<< HEAD
            message.setSubject("ChatPress Number Check");
=======
            message.setSubject("ChatPress Email Check");
>>>>>>> ca28459b8f9723fdfdea98152ce3285c9006dda6
            String body = "";
            body += "<h3>" + "요청하신 인증 번호입니다." + "</h3>";
            body += "<h1>" + number + "</h1>";
            body += "<h3>" + "감사합니다." + "</h3>";
            message.setText(body,"UTF-8", "html");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return message;
    }

    public int sendMail(String mail){



        MimeMessage message = CreateMail(mail);

        javaMailSender.send(message);

        return number;
    }
}
