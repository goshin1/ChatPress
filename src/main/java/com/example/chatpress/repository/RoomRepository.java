package com.example.chatpress.repository;

import com.example.chatpress.entity.ChatEntity;
import com.example.chatpress.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
    @Query(value = "select * from room where room_token = :roomToken", nativeQuery = true)
    Optional<RoomEntity> findByRoomToken(String roomToken);


}
