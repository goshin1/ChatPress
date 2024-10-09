package com.example.chatpress.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "share")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ShareEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long share_id;
    private String share_password;
    private String share_code;
    @ManyToOne
    @JoinColumn(name = "document_id")
    private DocumentEntity document;
}
