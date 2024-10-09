package com.example.chatpress.controller;

import com.example.chatpress.dto.ChatUserDto;
import com.example.chatpress.dto.RoomDto;
import com.example.chatpress.dto.UserDto;
import com.example.chatpress.service.RoomService;
import com.example.chatpress.socket.ChatRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/room")
@RestController
public class RoomController {
    @Autowired
    private RoomService roomService;

    @GetMapping("/list")
    public List<RoomDto> list(){
        List<RoomDto> roomList = roomService.list();
        return roomList;
    }

    @GetMapping("/ulist")
    public List<RoomDto> userList(){
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        return roomService.userList(userId);
    }

    @GetMapping("/create")
    public String createRoom(@RequestParam String name){
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("userID " + userId);
        if(userId == null) return "NO";
        return roomService.createRoom(userId, name);
    }

    //채팅방 찾기
    @GetMapping("/depart")
    public String departRoom(@RequestParam Long id){

        return roomService.findRoom(id);
    }

    // 채팅방 코드
    @GetMapping("/code")
    public String inviteCode(@RequestParam String code){
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        return roomService.inviteCode(userId, code);
    }

    // 멤버 목록 조회 수정
    @GetMapping("/members/{code}")
    public ResponseEntity<List<ChatUserDto>> memberSearch(@PathVariable String code){
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        List<ChatUserDto> dtos = roomService.memberSearch(code, userId);
        return dtos == null ? ResponseEntity.status(HttpStatus.BAD_REQUEST).build() :
            ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    // 채팅방 나가기
    @GetMapping("/exit/{code}")
    public ResponseEntity<RoomDto> exitRoom(@PathVariable String code){
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        String chcek = roomService.exit(code, userId);

        return chcek == null ? ResponseEntity.status(HttpStatus.BAD_REQUEST).build() :
                ResponseEntity.status(HttpStatus.OK).build();
    }

    // 채팅방 강퇴
    @GetMapping("forced")
    public String forcedExit(@RequestParam Long id, @RequestParam String code){
        return roomService.forcedExit(id, code);
    }



}
