package com.example.chatpress.repository;

import com.example.chatpress.entity.DocumentEntity;
import com.example.chatpress.entity.ShareEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ShareRepository extends JpaRepository<ShareEntity, Long> {

    @Query(value = "select * from share where share_code = :code", nativeQuery = true)
    Optional<ShareEntity> findByCode(String code);

    @Query(value = "select * from share where document_id = :documentId", nativeQuery = true)
    Optional<ShareEntity> findByDocumentId(Long documentId);
}
