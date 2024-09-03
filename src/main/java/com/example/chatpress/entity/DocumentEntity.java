package com.example.chatpress.entity;

import com.example.chatpress.dto.DocumentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "document")
@NoArgsConstructor
@AllArgsConstructor
public class DocumentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long document_id;
    private String document_path;
    private String document_user;
    private String document_name;

    public DocumentDto toDto(){
        return new DocumentDto(this.document_id, this.document_path, this.document_user, this.document_name);
    }
}
