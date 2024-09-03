package com.example.chatpress.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "connection")
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long conn_id;
    @ManyToOne
    @JoinColumn(name = "conn_user_id")
    private UserEntity user;
    @ManyToOne
    @JoinColumn(name = "conn_room_id")
    private RoomEntity room;
}
