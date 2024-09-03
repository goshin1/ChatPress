package com.example.chatpress.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserEditDto {
    private String id;
    private String email;
    private String password;
}
