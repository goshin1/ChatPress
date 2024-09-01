package com.example.chatpress.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChatUserDto {
    private Long id;
    private String user_nickname;
    private String user_icon_path;
}
