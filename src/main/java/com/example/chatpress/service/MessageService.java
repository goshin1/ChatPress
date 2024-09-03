package com.example.chatpress.service;

import com.example.chatpress.dto.FileDto;
import com.example.chatpress.entity.FileEntity;
import com.example.chatpress.entity.RoomEntity;
import com.example.chatpress.repository.FileRepository;
import com.example.chatpress.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
public class MessageService {

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private FileRepository fileRepository;
    public File write(FileDto fileDto, MultipartFile file) throws IOException, IllegalAccessException {
        RoomEntity roomEntity = roomRepository.findById(fileDto.getRoom_id())
                .orElseThrow(() -> new IllegalAccessException("해당 채팅방이 없습니다."));

        String projectPath = System.getProperty("user.dir") + "/images/";

        UUID uuid = UUID.randomUUID();
        String filename = uuid + "_" + file.getOriginalFilename();
        File saveFile = new File(projectPath, filename);
        file.transferTo(saveFile);
        fileDto.setFile_size(saveFile.length());
        fileDto.setFile_name(filename);
        fileDto.setFile_path("/webapp/"+filename);

        FileEntity fileEntity = new FileEntity(null, fileDto.getFile_type(), fileDto.getFile_name(), fileDto.getFile_path(), fileDto.getFile_size(), roomEntity);
        fileRepository.save(fileEntity);



        return saveFile;
    }


}
