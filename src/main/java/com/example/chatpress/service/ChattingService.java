package com.example.chatpress.service;

import com.example.chatpress.dto.ChatDto;
import com.example.chatpress.entity.ChatEntity;
import com.example.chatpress.entity.RoomEntity;
import com.example.chatpress.repository.ChatRepository;
import com.example.chatpress.repository.RoomRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChattingService {
    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Transactional
    public ChatDto chatInsert(ChatDto dto) {
        System.out.println(dto.toString());
        RoomEntity roomEntity = roomRepository.findById(dto.getRoom_id())
                .orElseThrow(() -> new IllegalArgumentException("해당 채팅 방이 없습니다."));
        ChatEntity entity = new ChatEntity(null, dto.getChat_user_nickname(), dto.getChat_message(), dto.getChat_type(), roomEntity);
        ChatEntity saved = chatRepository.save(entity);
        return saved.toDto();


    }

    public List<ChatDto> chatList(Long id) {
        List<ChatDto> chatEntities =  chatRepository.findAllByRoomId(id)
                .stream().map(ChatEntity::toDto)
                .toList();
        return chatEntities;
    }
}
