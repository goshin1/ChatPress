package com.example.chatpress.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DocumentLoadDto {
    private Long number;
    private String fileOrgName;
    private String innerHTML;
    private String password;
    private Long documentId;
}
