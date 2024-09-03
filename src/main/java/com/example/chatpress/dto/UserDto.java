package com.example.chatpress.dto;

import com.example.chatpress.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long user_id;
    private String username;
    private String password;
    private String role;
    private String user_email;
    private String user_nickname;
    private String user_icon_name;
    private String user_icon_path;

    public UserEntity toEntity(){
        return new UserEntity(this.user_id,
                this.username,
                this.password,
                this.role,
                this.user_email,
                this.user_nickname,
                this.user_icon_name,
                this.user_icon_path);
    }
}
