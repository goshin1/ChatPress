package com.example.chatpress.socket;

import com.example.chatpress.entity.RoomEntity;
import com.example.chatpress.repository.RoomRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {
    private final ObjectMapper objectMapper;
    private Map<String, ChatRoom> chatRooms;

    @Autowired
    private RoomRepository roomRepository;

    @PostConstruct
    private void init(){
        chatRooms = new LinkedHashMap<>();
        List<RoomEntity> roomEntityList = roomRepository.findAll();
        for(int i = 0; i < roomEntityList.size(); i++){
            RoomEntity roomEntity = roomEntityList.get(i);
            ChatRoom chatRoom = ChatRoom.builder()
                            .roomToken(roomEntity.getRoom_token())
                                    .roomName(roomEntity.getRoom_name())
                                            .build();
            chatRooms.put(roomEntity.getRoom_token(), chatRoom);
        }

    }

    public Collection<ChatRoom> findAllRoom(){
        return  chatRooms.values();
    }

    public ChatRoom findRoomById(String roomId){
        return chatRooms.get(roomId);
    }

    public ChatRoom createRoom( String name){
        String randomId = UUID.randomUUID().toString();
        ChatRoom chatRoom = ChatRoom.builder()
                .roomToken(randomId)
                .roomName(name)
                .build();
        chatRooms.put(randomId, chatRoom);
        return chatRoom;
    }



}
