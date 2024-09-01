package com.example.chatpress.controller;

import com.example.chatpress.dto.AgreeDto;
import com.example.chatpress.service.AgreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/agree")
@RestController
public class AgreeController {

    @Autowired
    private AgreeService agreeService;

    @PostMapping("/join")
    public ResponseEntity<AgreeDto> agreeCheck(@RequestBody AgreeDto dto){
        String check = agreeService.check(dto);
        return check.equals("ok") ? ResponseEntity.status(HttpStatus.OK).build() : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
