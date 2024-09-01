package com.example.chatpress.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DocumentSaveDto {
    // 문서 내용
    private Long number;
    private String fileOrgName;
    private String innerHTML;
}
