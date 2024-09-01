package com.example.chatpress.dto;

import com.example.chatpress.entity.ChatEntity;
import com.example.chatpress.entity.RoomEntity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChatDto {
    private Long chat_id;
    private String chat_user_nickname;
    private String chat_message;
    private String chat_type;
    private Long room_id;

}
