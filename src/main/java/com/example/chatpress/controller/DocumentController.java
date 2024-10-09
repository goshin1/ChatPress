package com.example.chatpress.controller;

import com.example.chatpress.dto.DocumentDto;
import com.example.chatpress.dto.DocumentSaveDto;
import com.example.chatpress.service.DocumentService;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/document")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    // 처음 저장하는 파일인 경우
    @PostMapping("/new")
    public String documentNew(@RequestBody DocumentSaveDto dto) throws IOException {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        if(userId == null || userId.equals("")) return null;

        String check = documentService.newDocument(dto.getInnerHTML(), userId, dto.getFileOrgName());

        return check;
    }
    
    // 이미 생성된 파일을 수정
    @PostMapping("/save")
    public String documentSave(@RequestBody DocumentSaveDto dto) throws IOException {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        if(userId == null || userId.equals("")) return null;
        String check = documentService.saveDocument(dto.getInnerHTML(), userId, dto.getFileOrgName());
        return check;
    }

    // 유저에 작성한 문서 목록
    @GetMapping("/list")
    public List<DocumentDto> documentDtoList(){
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        if(userId == null || userId.equals("")) return null;
        List<DocumentDto> dtos = documentService.getList(userId);
        return dtos;
    }

    @GetMapping("/load")
    public DocumentSaveDto documentLoad(@RequestParam String document) throws IOException {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        if(userId == null || userId.equals("")) return null;
        DocumentSaveDto loadDocument = documentService.load(userId, document);

        return loadDocument;
    }

    // 문서 삭제
    @GetMapping("/delete")
    public ResponseEntity<String> documentDelete(@RequestParam Long documentId){
        String check = documentService.delete(documentId);

        return check == null ?
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build() :
                ResponseEntity.status(HttpStatus.OK).build();
    }

}
