package com.example.chatpress.repository;

import com.example.chatpress.entity.ConnectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ConnectionRepository extends JpaRepository<ConnectionEntity, Long> {

    @Query(value = "select * from connection where conn_user_id = :userId", nativeQuery = true)
    List<ConnectionEntity> findByConnUserId(Long userId);

    @Query(value = "select * from connection where conn_room_id = :roomId and conn_user_id = :userId", nativeQuery = true)
    Optional<ConnectionEntity> findByRoomAndUser(Long roomId, Long userId);

    @Query(value = "select * from connection where conn_room_id = :roomId", nativeQuery = true)
    List<ConnectionEntity> findAllByRoomId(Long roomId);

    @Query(value = "select * from connection where conn_user_id = :id and conn_room_id = :roomId", nativeQuery = true)
    Optional<ConnectionEntity> findByUserAndRoom(Long id, Long roomId);
}
