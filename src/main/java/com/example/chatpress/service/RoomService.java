package com.example.chatpress.service;

import com.example.chatpress.dto.ChatUserDto;
import com.example.chatpress.dto.RoomDto;
import com.example.chatpress.dto.UserDto;
import com.example.chatpress.entity.ConnectionEntity;
import com.example.chatpress.entity.RoomEntity;
import com.example.chatpress.entity.UserEntity;
import com.example.chatpress.repository.ChatRepository;
import com.example.chatpress.repository.ConnectionRepository;
import com.example.chatpress.repository.RoomRepository;
import com.example.chatpress.repository.UserRepository;
import com.example.chatpress.socket.ChatRoom;
import com.example.chatpress.socket.ChatService;
import jakarta.transaction.Transactional;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ConnectionRepository connectionRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ChatService chatService;

    public List<RoomDto> list() {
        List<RoomEntity> entityList = roomRepository.findAll();
        return entityList.stream()
                .map(entity -> new RoomDto(entity.getRoom_id(), entity.getRoom_token(), entity.getRoom_name(), entity.getRoom_leader()))
                .toList();
    }

    @Transactional
    public List<RoomDto> userList(String userid) {
        UserEntity user = userRepository.findByUserId(userid).orElse(null);
        if(user == null) return null;


        List<ConnectionEntity> connList = connectionRepository.findByConnUserId(user.getId());
        List<RoomDto> roomList = new ArrayList<>();
        for(int i =0; i < connList.size(); i++){
            RoomEntity roomEntity = roomRepository.findById(connList.get(i).getRoom().getRoom_id()).orElse(null);

            if (roomEntity != null){
                roomList.add(roomEntity.toDto());
            }
        }
        return roomList;
    }


    @Transactional
    public String createRoom(String userId, String name) {
        UserEntity user = userRepository.findByUserId(userId).orElse(null);
        if(user == null) return "NO";

        ChatRoom chatRoom = chatService.createRoom(name);
        RoomEntity room = new RoomEntity(null, chatRoom.getRoomToken(), chatRoom.getRoomName(), userId);
        RoomEntity saved = roomRepository.save(room);

        ConnectionEntity connectionEntity = new ConnectionEntity(null, user, saved);
        connectionRepository.save(connectionEntity);
        return "OK";
    }

    public String findRoom(Long id) {
        RoomEntity entity = roomRepository.findById(id).orElse(null);
        return entity != null ? entity.getRoom_token() : null;
    }

    @Transactional
    public String inviteCode(String loginId, String code) {
        UserEntity user = userRepository.findByUserId(loginId).orElseThrow(() -> new IllegalArgumentException("유저 정보가 없습니다."));
        RoomEntity room = roomRepository.findByRoomToken(code).orElseThrow(() -> new IllegalArgumentException("해당 방이 없습니다."));

        ConnectionEntity duplicateConnection = connectionRepository.findByRoomAndUser(room.getRoom_id(), user.getId()).orElse(null);
        if(duplicateConnection != null) return null;

        ConnectionEntity connectionEntity = new ConnectionEntity(null, user, room);
        connectionRepository.save(connectionEntity);
        return "Ok";
    }

    @Transactional
    public String exit(String code, String userId) {
        RoomEntity room = roomRepository.findByRoomToken(code).orElse(null);
        UserEntity user = userRepository.findByUserId(userId).orElse(null);
        if(room == null || user == null)
            return null;
        ConnectionEntity connection = connectionRepository.findByRoomAndUser(room.getRoom_id(), user.getId()).orElse(null);
        if(connection == null)
            return null;
        connectionRepository.delete(connection);

        List<ConnectionEntity> existsConnections = connectionRepository.findAllByRoomId(room.getRoom_id());

        if(existsConnections.isEmpty()){
            chatRepository.deleteByRoomId(room.getRoom_id());
        }
        return "Ok";
    }

    @Transactional
    public List<ChatUserDto> memberSearch(String code, String userId) {
        // 1.해당 방의 방번호를 조회
        RoomEntity roomEntity = roomRepository.findByRoomToken(code).orElse(null);
        if(roomEntity == null) return null;
        // 2.조회된 방번호를 통해 connection 을 조회
        List<ConnectionEntity> connectionEntityList = connectionRepository.findAllByRoomId(roomEntity.getRoom_id());
        // 3.조회된 배열을 돌면서 해당 유저를 찾아 아이콘 정보와 닉네임을 가져와 ChatUserDto배열을 생성

        List<ChatUserDto> dtoList = new ArrayList<>();
        for(int i = 0; i < connectionEntityList.size(); i++){
            UserEntity user = userRepository.findById(connectionEntityList.get(i).getUser().getId()).orElse(null);
            if(user == null) continue;
            ChatUserDto dto = new ChatUserDto();
            dto.setId(user.getId());
            dto.setUser_nickname(user.getUser_nickname());
            dto.setUser_icon_path(user.getUser_icon_path());
            dtoList.add(dto);
        }

        // 4.해당 배열을 반환
        
        return dtoList;
    }

    public String forcedExit(Long id, String code) {
        RoomEntity room = roomRepository.findByRoomToken(code).orElse(null);
        UserEntity user = userRepository.findById(id).orElse(null);
        if(room == null || user == null)
            return null;
        ConnectionEntity connection = connectionRepository.findByRoomAndUser(room.getRoom_id(), user.getId()).orElse(null);
        if(connection == null)
            return null;
        connectionRepository.delete(connection);
        return "Ok";
    }
}
