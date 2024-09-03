package com.example.chatpress.service;

import com.example.chatpress.dto.DocumentDto;
import com.example.chatpress.dto.DocumentSaveDto;
import com.example.chatpress.entity.DocumentEntity;
import com.example.chatpress.repository.DocumentRepository;
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

    public String newDocument(String innerHTML, String userId, String fileOrgName) throws IOException {
        DocumentEntity dentity = documentRepository.findByUserAndFileOrgName(userId, fileOrgName).orElse(null);
        if(dentity != null) return "duplicate";

        String projectPath = System.getProperty("user.dir") + "/document/";
        UUID uuid = UUID.randomUUID();
        String filename = uuid + "_" + fileOrgName;
        File saveFile = new File(projectPath, filename);
        saveFile.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile, true));
        writer.write(innerHTML);
        writer.flush();
        writer.close();
        DocumentEntity entity = new DocumentEntity(null, projectPath + filename, userId, fileOrgName);
        DocumentEntity saveEntity = documentRepository.save(entity);

        return saveEntity.getDocument_path();
    }

    public String saveDocument(String innerHTML, String userId, String fileOrgName) throws IOException {
        DocumentEntity entity = documentRepository.findByUserAndFileOrgName(userId, fileOrgName).orElse(null);
        if(entity == null) return null;
        File writerFile = new File(entity.getDocument_path());
        BufferedWriter writer = new BufferedWriter(new FileWriter(writerFile, false));
        writer.write(innerHTML);
        writer.flush();
        writer.close();
        return entity.getDocument_path();
    }

    public List<DocumentDto> getList(String userId) {
        List<DocumentDto> dtos = documentRepository.findAllByUser(userId)
                .stream().map(DocumentEntity::toDto).toList();
        return dtos;
    }

    public DocumentSaveDto load(String userId, String document) throws IOException {
        // DB를 조사하여 엔티티를 찾고 엔티티를 통해 문서를 읽어 그 내용을 String에 담아서 전달
        DocumentEntity entity = documentRepository.findByUserAndFileOrgName(userId, document).orElse(null);
        if (entity == null) return null;
        File loadFile = new File(entity.getDocument_path());
        BufferedReader reader = new BufferedReader(new FileReader(loadFile));
        String innerHTML = "";
        String str;
        while ((str = reader.readLine()) != null){
            innerHTML += str;
        }
        reader.close();
        DocumentSaveDto dto = new DocumentSaveDto(entity.getDocument_id(), entity.getDocument_name(), innerHTML);
        return dto;
    }
}
