package com.example.chatpress.dto;

import com.example.chatpress.entity.DocumentEntity;
import com.example.chatpress.entity.ShareEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShareDto {
    private Long share_id;
    private String share_password;
    private String share_code;
    private Long document_id;

    public ShareEntity toEntity() {
        return new ShareEntity(this.share_id, this.share_password, this.share_code, null);
    }
}
