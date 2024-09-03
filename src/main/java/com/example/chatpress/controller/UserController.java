package com.example.chatpress.controller;

import com.example.chatpress.dto.*;
import com.example.chatpress.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    // 회원가입 시 중복되는 아이디, 이메일이 있는지 검사
    @GetMapping("/id")
    public String duplicateId(@RequestParam String id, @RequestParam String email){
        return userService.duplicateId(id, email);
    }

    // 회원가입 처리
    @PostMapping("/join")
    public String join(UserDto userDto, MultipartFile file) throws IOException {
        System.out.println(userDto.toString());
        return  userService.join(userDto, file);
    }


    @PostMapping("/edit")
    public String edit(UserDto userDto, MultipartFile file) throws IOException {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(userDto.toString());
        return  userService.edit(userDto, file, userId);
    }

    // 로그인 유저 정보 조회
    @GetMapping("/info")
    public ChatUserDto loginInfo(){
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.loginInfo(userId);
    }

    // 메일 인증
    @GetMapping("/search")
    public UserLoginDto searchUser(@RequestParam String mail){

        return userService.searchUser(mail);
    }

    // 회원 정보 변경 시 중복 아이디, 이메일 검사 및 기존 비밀번호 확인
    @PostMapping("/check")
    public String checkEdit(@RequestBody  UserEditDto userEditDto){
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        return userService.checkEdit(userId, userEditDto);

    }






}
