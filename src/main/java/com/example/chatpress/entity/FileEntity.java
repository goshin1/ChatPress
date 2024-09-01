package com.example.chatpress.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "file")
@NoArgsConstructor
@AllArgsConstructor
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long file_id;
    private String file_type;
    private String file_name;
    private String file_path;
    private Long file_size;
    @ManyToOne
    @JoinColumn(name = "room_id")
    private RoomEntity room;

}
