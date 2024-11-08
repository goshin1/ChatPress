package com.example.chatpress.entity;

import com.example.chatpress.dto.UserDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "chatuser")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String role;
    private String user_email;
    private String user_nickname;
    private String user_icon_name;
    private String user_icon_path;

    public void patch(UserEntity entity){
        if(entity.getUsername() != null)
            this.username = entity.getUsername();
        if(entity.getPassword() != null)
            this.password = entity.getPassword();
        if(entity.getRole() != null)
            this.role = entity.getRole();
        if(entity.getUser_email() != null)
            this.user_email = entity.getUser_email();
        if(entity.getUser_nickname() != null)
            this.user_nickname = entity.getUser_nickname();
        if(entity.getUser_icon_name() != null)
            this.user_icon_name = entity.getUser_icon_name();
        if(entity.getUser_icon_path() != null)
            this.user_icon_path = entity.getUser_icon_path();
    }

    public UserDto toDto(){
        return new UserDto(
                this.id,
                this.username,
                this.password,
                this.role,
                this.user_email,
                this.user_nickname,
                this.user_icon_name,
                this.user_icon_path);
    }
}
