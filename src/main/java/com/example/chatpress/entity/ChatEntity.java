package com.example.chatpress.entity;

import com.example.chatpress.dto.ChatDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "chat")
@NoArgsConstructor
@AllArgsConstructor
public class ChatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chat_id;
    private String chat_user_nickname;
    private String chat_message;
    private String chat_type;
    @ManyToOne
    @JoinColumn(name = "room_id")
    private RoomEntity room;

    public ChatDto toDto() {
        return new ChatDto(
                this.chat_id,
                this.chat_user_nickname,
                this.chat_message,
                this.chat_type,
                this.room.getRoom_id()
        );
    }
}
