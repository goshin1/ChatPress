package com.example.chatpress.controller;

import com.example.chatpress.dto.MailDto;
import com.example.chatpress.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/mail")
    public String mainConfirm(@RequestBody  MailDto mailDto){
        int number = emailService.sendMail(mailDto.getMail());
        String num = "" + number;
        System.out.println("이메일 전송 : "+num);
        return num;

    }
}
