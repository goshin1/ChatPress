package com.example.chatpress.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "agree")
@NoArgsConstructor
@AllArgsConstructor
public class AgreeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long agree_id;
    private String agree_time;
    private Long agree_email;
    private Long agree_info;
    private String agree_username;
}
