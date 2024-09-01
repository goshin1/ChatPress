package com.example.chatpress.dto;

import com.example.chatpress.entity.DocumentEntity;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDto {
    private Long document_id;
    private String document_path;
    private String document_user;
    private String document_name;
    public DocumentEntity toEntity(){
        return new DocumentEntity(this.document_id, this.document_path, this.document_user, this.document_name);
    }
}
