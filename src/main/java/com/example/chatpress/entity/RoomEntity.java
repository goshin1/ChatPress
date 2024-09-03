package com.example.chatpress.entity;

import com.example.chatpress.dto.RoomDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "room")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long room_id;
    private String room_token;
    private String room_name;
    private String room_leader;

    public RoomDto toDto(){
        return new RoomDto(this.room_id, this.room_token, this.room_name, this.room_leader);
    }

}
