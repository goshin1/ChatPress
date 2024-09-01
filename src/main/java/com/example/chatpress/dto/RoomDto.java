package com.example.chatpress.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {
    private Long room_id;
    private String room_token;
    private String room_name;
    private String room_leader;
}
