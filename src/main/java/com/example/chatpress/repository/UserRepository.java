package com.example.chatpress.repository;

import com.example.chatpress.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query(value = "select * from user where username = :username and password = :password", nativeQuery = true)
    Optional<UserEntity> findByLogin(String username, String password);

    @Query(value = "select * from user where username = :username", nativeQuery = true)
    Optional<UserEntity> findByUserId(String username);

    @Query(value = "select * from user where user_email = :email", nativeQuery = true)
    Optional<UserEntity> findByEmail(String email);

    Boolean existsByUsername(String username);
    Optional<UserEntity> findByUsername(String username);
}
