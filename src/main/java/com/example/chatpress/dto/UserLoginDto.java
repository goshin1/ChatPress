package com.example.chatpress.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDto {
    private String username;
    private String password;
}
