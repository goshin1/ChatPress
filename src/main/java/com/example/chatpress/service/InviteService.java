package com.example.chatpress.service;

import com.example.chatpress.dto.InviteDto;
import com.example.chatpress.dto.RoomDto;
import com.example.chatpress.entity.ConnectionEntity;
import com.example.chatpress.entity.InviteEntity;
import com.example.chatpress.entity.RoomEntity;
import com.example.chatpress.entity.UserEntity;
import com.example.chatpress.repository.ConnectionRepository;
import com.example.chatpress.repository.InviteRepository;
import com.example.chatpress.repository.RoomRepository;
import com.example.chatpress.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InviteService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ConnectionRepository connectionRepository;


    @Autowired
    private InviteRepository inviteRepository;

    @Transactional
    public InviteDto invite(InviteDto dto) {
        UserEntity user = userRepository.findByUsername(dto.getInvite_username()).orElse(null);
        if(user == null) return null;
        RoomEntity room = roomRepository.findByRoomToken(dto.getInvite_room_token()).orElse(null);
        if(room == null) return null;
        ConnectionEntity connection = connectionRepository.findByUserAndRoom(user.getId(), room.getRoom_id()).orElse(null);
        if(connection != null) return null;
        InviteEntity inviteEntity = inviteRepository.save(dto.toEntity());
        return inviteEntity.toDto();

    }

    public List<RoomDto> list(String userId) {
        List<RoomDto> dtos = new ArrayList<>();
        List<InviteEntity> entities = inviteRepository.findAllByInviteUsername(userId);
        for(int i = 0; i < entities.size(); i++){
            RoomEntity room = roomRepository.findByRoomToken(entities.get(i).getInvite_room_token()).orElse(null);
            if(room == null) continue;
            dtos.add(room.toDto());
        }
        return dtos;
    }

    public String exitInvite(String username, String code) {
        InviteEntity inviteEntity = inviteRepository.findByInviteRoomToken(username, code).orElse(null);
        if(inviteEntity == null) return null;
        inviteRepository.delete(inviteEntity);
        return "ok";
    }
}
