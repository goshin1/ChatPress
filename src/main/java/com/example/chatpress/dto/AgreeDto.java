package com.example.chatpress.dto;

import com.example.chatpress.entity.AgreeEntity;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AgreeDto {
    private Long agree_id;
    private String agree_time;
    private Long agree_email;
    private Long agree_info;
    private String agree_username;

    public AgreeEntity toEntity(){
        return new AgreeEntity(this.agree_id, this.agree_time, this.agree_email, this.agree_info, this.agree_username);
    }
}
