package com.example.chatpress.service;

import com.example.chatpress.dto.DocumentDto;
import com.example.chatpress.dto.DocumentSaveDto;
import com.example.chatpress.entity.DocumentEntity;
import com.example.chatpress.entity.ShareEntity;
import com.example.chatpress.repository.DocumentRepository;
import com.example.chatpress.repository.ShareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.io.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private ShareRepository shareRepository;

    public String newDocument(String innerHTML, String userId, String fileOrgName) throws IOException {
        DocumentEntity dentity = documentRepository.findByUserAndFileOrgName(userId, fileOrgName).orElse(null);
        if(dentity != null) return "duplicate";

        String projectPath = "/root/ChatPress/document/";
        UUID uuid = UUID.randomUUID();
        String filename = uuid + "_" + fileOrgName;
        File saveFile = new File(projectPath, filename);
        saveFile.createNewFile();
        BufferedWriter writer =  new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveFile, true), "utf-8"));
        writer.write(innerHTML);
        writer.flush();
        writer.close();
        DocumentEntity entity = new DocumentEntity(null, projectPath + filename, userId, fileOrgName);
        DocumentEntity saveEntity = documentRepository.save(entity);

        return saveEntity.getDocument_id().toString();
    }

    public String saveDocument(String innerHTML, String userId, String fileOrgName) throws IOException {
        DocumentEntity entity = documentRepository.findByUserAndFileOrgName(userId, fileOrgName).orElse(null);
        if(entity == null) return null;
        File writerFile = new File(entity.getDocument_path());
        BufferedWriter writer =  new BufferedWriter(new OutputStreamWriter(new FileOutputStream(writerFile, true), "utf-8"));
        writer.write(innerHTML);
        writer.flush();
        writer.close();
        return entity.getDocument_id().toString();
    }

    public List<DocumentDto> getList(String userId) {
        List<DocumentDto> dtos = documentRepository.findAllByUser(userId)
                .stream().map(DocumentEntity::toDto).toList();
        return dtos;
    }

    public DocumentSaveDto load(String userId, String document) throws IOException {
        DocumentEntity entity = documentRepository.findByUserAndFileOrgName(userId, document).orElse(null);
        if (entity == null) return null;
        File loadFile = new File(entity.getDocument_path());
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(loadFile), "utf-8"));
        String innerHTML = "";
        String str;
        while ((str = reader.readLine()) != null){
            innerHTML += str;
        }
        reader.close();
        DocumentSaveDto dto = new DocumentSaveDto(entity.getDocument_id(), entity.getDocument_name(), innerHTML);
        return dto;
    }

    public String delete(Long documentId) {
        ShareEntity share = shareRepository.findByDocumentId(documentId).orElse(null);
        if(share == null) return null;
        shareRepository.delete(share);

        DocumentEntity document = documentRepository.findById(documentId).orElse(null);
        if(document == null) return null;
        documentRepository.delete(document);
        return "ok";
    }
}
