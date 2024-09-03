package com.example.chatpress.repository;

import com.example.chatpress.entity.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatRepository extends JpaRepository<ChatEntity, Long> {
    @Query(value = "select * from chat where room_id = :roomId", nativeQuery = true)
    List<ChatEntity> findAllByRoomId(Long roomId);
}
