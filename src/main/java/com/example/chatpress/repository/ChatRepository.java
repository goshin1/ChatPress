package com.example.chatpress.repository;

import com.example.chatpress.entity.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatRepository extends JpaRepository<ChatEntity, Long> {
    @Query(value = "select * from chat where room_id = :roomId", nativeQuery = true)
    List<ChatEntity> findAllByRoomId(Long roomId);

    @Modifying(clearAutomatically = true)
    @Query(value = "update chat set chat_user_nickname = :newName where chat_user_nickname = :oldName", nativeQuery = true)
    void updateChat(String newName, String oldName);

    @Modifying(clearAutomatically = true)
    @Query(value = "delete from chat where room_id = :room_id", nativeQuery = true)
    void deleteByRoomId(Long room_id);
}
