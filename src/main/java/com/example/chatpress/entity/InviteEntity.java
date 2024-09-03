package com.example.chatpress.entity;

import com.example.chatpress.dto.InviteDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "invite")
@NoArgsConstructor
@AllArgsConstructor
public class InviteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long invite_id;
    private String invite_room_token;
    private String invite_username;


    public InviteDto toDto(){
        return new InviteDto(
                this.invite_id,
                this.invite_room_token,
                this.invite_username
        );
    }
}
