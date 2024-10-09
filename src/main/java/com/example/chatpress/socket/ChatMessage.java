package com.example.chatpress.socket;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChatMessage {
    private String chatRoomId; // 채팅방 고유 ID
    private String message; // 보낼 메세지
    private String type; // 소켓 타입
    private String user; // 작성자
    private String fileType; // 메세지 타입
}
