package com.example.chatpress.socket;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChatMessage {
    private String chatRoomId;
    private String message;
    private String type;
    private String user;
    private String fileType;
}
