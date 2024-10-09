package com.example.chatpress.service;

import com.example.chatpress.dto.*;
import com.example.chatpress.entity.UserEntity;
import com.example.chatpress.repository.ChatRepository;
import com.example.chatpress.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatRepository chatRepository;


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    //
    public UserDto login(LoginDto loginDto) {
        System.out.println(loginDto.toString());
        UserEntity loginUser = userRepository.findByLogin(loginDto.getUsername(), loginDto.getPassword()).orElse(null);
        return loginUser.toDto();
    }

    @Transactional
    public String join(UserDto userDto, MultipartFile file) throws IOException {
        String projectPath = System.getProperty("user.dir") + "/images/icons/";

        UUID uuid = UUID.randomUUID();
        String filename = uuid + "_" + file.getOriginalFilename();
        File saveFile = new File(projectPath, filename);
        file.transferTo(saveFile);
        UserEntity userCheck = userRepository.findByUserId(userDto.getUsername()).orElse(null);

        if(userCheck != null) return null;

        userDto.setUser_icon_name(filename);
        userDto.setUser_icon_path("/attach/images/icons/" + filename);
        userDto.setRole("ROLE_USER");
        userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

        userRepository.save(userDto.toEntity());
        return "Ok";
    }


    @Transactional
    public String edit(UserDto userDto, MultipartFile file, String userId) throws IOException {
        System.out.println("===== check =====");
        // 여기서는 변경이이루어진다

        if(file != null){
            String projectPath = System.getProperty("user.dir") + "/images/icons/";
            UUID uuid = UUID.randomUUID();
            String filename = uuid + "_" + file.getOriginalFilename();
            File saveFile = new File(projectPath, filename);
            file.transferTo(saveFile);
            userDto.setUser_icon_name(filename);
            userDto.setUser_icon_path("/attach/images/icons/" + filename); // 아이콘은 바로 받자마자 사용할 수 있게
        }


        UserEntity exists = userRepository.findByUsername(userId).orElse(null);

        if(exists == null) return null;

        chatRepository.updateChat(userDto.getUser_nickname(), exists.getUser_nickname());

        userDto.setRole("ROLE_USER");
        userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

        UserEntity update = userDto.toEntity();
        exists.patch(update);

        userRepository.save(exists);






        return "ok";
    }


    public String duplicateId(String id, String email) {
        UserEntity idCheck = userRepository.findByUserId(id).orElse(null);
        if(idCheck != null) return "id";
        UserEntity emailCheck = userRepository.findByEmail(email).orElse(null);
        if(emailCheck != null) return "email";
        return "ok";
    }

    public ChatUserDto loginInfo(String username) {
        UserEntity userEntity = userRepository.findByUsername(username).orElse(null);
        if(userEntity == null)
            return null;
        ChatUserDto dto = new ChatUserDto();
        dto.setId(userEntity.getId());
        dto.setUser_nickname(userEntity.getUser_nickname());
        dto.setUser_icon_path(userEntity.getUser_icon_path());

        return dto;
    }

    public UserLoginDto searchUser(String mail) {
        // 해당 이메일에 계정 찾기
        UserEntity userEntity = userRepository.findByEmail(mail).orElse(null);
        if(userEntity == null)
            return null;

        // 해당 계정 임시 비밀번호로 변경
        String newPassword = ""+((int)(Math.random() * (90000)) + 100000);
        userEntity.setPassword(bCryptPasswordEncoder.encode(newPassword));
        UserEntity updatePassword = userRepository.save(userEntity);


        UserLoginDto loginDto = new UserLoginDto();
        loginDto.setUsername(updatePassword.getUsername());
        loginDto.setPassword(newPassword);
        return loginDto;
    }

    public String checkEdit(String userId, UserEditDto userEditDto) {
        // 아이디를 통해 유저를 조회
        UserEntity userEntity = userRepository.findByUsername(userId).orElse(null);
        if(userEntity == null) return "no";
        // 입력 받은 비밀번호가 맞는지 확인
        if(!bCryptPasswordEncoder.matches(userEditDto.getPassword(), userEntity.getPassword())) return "password";
        // 아이디 및 이메일 중복 확인
        UserEntity idCheck = userRepository.findByUserId(userEditDto.getId()).orElse(null);
        if(idCheck != null && !userEntity.getUsername().equals(userEditDto.getId())) return "id";
        UserEntity emailCheck = userRepository.findByEmail(userEditDto.getEmail()).orElse(null);
        if(emailCheck != null && !userEntity.getUser_email().equals(userEditDto.getEmail())) return "email";
        return "ok";

    }

    public UserDto beforeEdit(String userId) {
        UserEntity entity = userRepository.findByUserId(userId).orElse(null);
        if(entity == null) return null;
        UserDto dto = new UserDto();
        dto.setUsername(userId);
        dto.setUser_nickname(entity.getUser_nickname());
        dto.setUser_email(entity.getUser_email());
        dto.setUser_icon_path(entity.getUser_icon_path());
        return dto;
    }
}
