package com.example.chatpress.service;

import com.example.chatpress.dto.DocumentLoadDto;
import com.example.chatpress.dto.DocumentSaveDto;
import com.example.chatpress.dto.ShareDto;
import com.example.chatpress.entity.DocumentEntity;
import com.example.chatpress.entity.ShareEntity;
import com.example.chatpress.repository.DocumentRepository;
import com.example.chatpress.repository.ShareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;


@Service
public class ShareService {
    @Autowired
    private ShareRepository shareRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private DocumentService documentService;

    public ShareDto createShare(ShareDto dto) {
        DocumentEntity document = documentRepository.findById(dto.getDocument_id()).orElse(null);
        if(document == null) return null;
        ShareEntity share = dto.toEntity();
        share.setDocument(document);
        String uuid;

        while(true){
            uuid = UUID.randomUUID().toString().substring(0, 15);
            ShareEntity exists = shareRepository.findByCode(uuid).orElse(null);
            System.out.println("res : " + exists);
            if(exists == null){
                break;
            }
        }

        share.setShare_code(uuid);
        ShareEntity save = shareRepository.save(share);

        return new ShareDto(save.getShare_id(), save.getShare_password(), save.getShare_code(), save.getDocument().getDocument_id());
    }

    public ShareDto loadShare(Long documentId) {
        ShareEntity share = shareRepository.findByDocumentId(documentId).orElse(null);
        return share == null ?
                null :
                new ShareDto(share.getShare_id(), share.getShare_password(), share.getShare_code(), share.getDocument().getDocument_id());


    }

    public DocumentLoadDto loadDocument(String code) throws IOException {
        ShareEntity share = shareRepository.findByCode(code).orElse(null);
        if(share == null) return null;
        DocumentEntity document = documentRepository.findById(share.getDocument().getDocument_id()).orElse(null);
        if(document == null) return null;

        DocumentSaveDto dto = documentService.load(document.getDocument_user(), document.getDocument_name());
        DocumentLoadDto load = new DocumentLoadDto(dto.getNumber(), dto.getFileOrgName(), dto.getInnerHTML(), share.getShare_password(), share.getDocument().getDocument_id());
        return load;
    }
}
