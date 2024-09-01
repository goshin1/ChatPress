package com.example.chatpress.dto;

import com.example.chatpress.entity.InviteEntity;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InviteDto {
    private Long invite_id;
    private String invite_room_token;
    private String invite_username;
    public InviteEntity toEntity(){
        return new InviteEntity(
                this.invite_id,
                this.invite_room_token,
                this.invite_username
        );
    }
}
