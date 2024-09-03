package com.example.chatpress.repository;

import com.example.chatpress.dto.InviteDto;
import com.example.chatpress.entity.InviteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InviteRepository extends JpaRepository<InviteEntity, Long> {

    @Query(value = "select * from invite where invite_username = :invite_username", nativeQuery = true)
    List<InviteEntity> findAllByInviteUsername(String invite_username);

    @Query(value = "select * from invite where invite_username = :invite_username and invite_room_token = :invite_room_token", nativeQuery = true)
    Optional<InviteEntity> findByInviteRoomToken(String invite_username, String invite_room_token);
}
