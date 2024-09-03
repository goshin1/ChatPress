package com.example.chatpress.controller;

import com.example.chatpress.dto.FileDto;
import com.example.chatpress.entity.FileEntity;
import com.example.chatpress.service.MessageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping("/upload")
    public String upload(FileDto fileDto, MultipartFile file, HttpServletRequest request) throws IOException, IllegalAccessException {
        File resource = messageService.write(fileDto, file);

        return "/attach/images/" + resource.getName();

    }


}
