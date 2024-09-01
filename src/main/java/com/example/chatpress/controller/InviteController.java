package com.example.chatpress.controller;

import com.example.chatpress.dto.InviteDto;
import com.example.chatpress.dto.RoomDto;
import com.example.chatpress.entity.InviteEntity;
import com.example.chatpress.service.InviteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invite")
public class InviteController {
    @Autowired
    private InviteService inviteService;

    @PostMapping("/member")
    public ResponseEntity<InviteDto> inviteMember(InviteDto dto){
        System.out.println(dto.toString());
        InviteDto invite = inviteService.invite(dto);
        return invite == null ?
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build() :
                ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<RoomDto>> inviteList(){
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        List<RoomDto> roomDtos = inviteService.list(userId);
        return roomDtos == null ?
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build() :
                ResponseEntity.status(HttpStatus.OK).body(roomDtos);

    }

    @GetMapping("/exit")
    public ResponseEntity<Object> exitInvite(@RequestParam String code){
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        String check = inviteService.exitInvite(userId, code);
        return check != null ?
                ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
