package com.example.chatpress.controller;

import com.example.chatpress.dto.DocumentLoadDto;
import com.example.chatpress.dto.DocumentSaveDto;
import com.example.chatpress.dto.ShareDto;
import com.example.chatpress.service.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/share")
public class ShareController {
    @Autowired
    private ShareService service;

    @PostMapping("/new")
    public ResponseEntity<ShareDto> createShare(@RequestBody ShareDto dto){
        ShareDto createShare = service.createShare(dto);
        return createShare == null ?
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build() :
                ResponseEntity.status(HttpStatus.OK).body(createShare);
    }

    @GetMapping("/load")
    public ResponseEntity<ShareDto> loadShare(@RequestParam Long documentId){
        ShareDto loadShare = service.loadShare(documentId);
        return loadShare == null ?
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build() :
                ResponseEntity.status(HttpStatus.OK).body(loadShare);
    }

    @GetMapping("/load/document")
    public ResponseEntity<DocumentLoadDto> loadShareDocument(@RequestParam String code) throws IOException {

        DocumentLoadDto loadDocument = service.loadDocument(code);
        return loadDocument == null ?
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build() :
                ResponseEntity.status(HttpStatus.OK).body(loadDocument);
    }

}
