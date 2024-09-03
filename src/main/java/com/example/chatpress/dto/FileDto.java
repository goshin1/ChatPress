package com.example.chatpress.dto;

import com.example.chatpress.entity.FileEntity;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FileDto {
    private Long file_id;
    private String file_type;
    private String file_name;
    private String file_path;
    private Long file_size;
    private Long room_id;

}
