package com.example.chatpress.controller;

import com.example.chatpress.dto.ChatDto;
import com.example.chatpress.service.ChattingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/chat")
@RestController
public class ChattingController {
    @Autowired
    private ChattingService chattingService;
    @PostMapping("/insert")
    public ChatDto chatInsert(@RequestBody ChatDto dto){

        return chattingService.chatInsert(dto);
    }

    @GetMapping("/list")
    public List<ChatDto> chatList(@RequestParam Long id){
        return chattingService.chatList(id);
    }
}
