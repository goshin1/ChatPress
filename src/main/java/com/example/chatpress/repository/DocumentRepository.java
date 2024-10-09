package com.example.chatpress.repository;

import com.example.chatpress.entity.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.print.Doc;
import java.util.List;
import java.util.Optional;

public interface DocumentRepository extends JpaRepository<DocumentEntity, Long> {
    @Query(value = "select * from document where document_user = :user and document_name = :filename", nativeQuery = true)
    Optional<DocumentEntity> findByUserAndFileOrgName(String user, String filename);

    @Query(value = "select * from document where document_user = :user", nativeQuery = true)
    List<DocumentEntity> findAllByUser(String user);


}
