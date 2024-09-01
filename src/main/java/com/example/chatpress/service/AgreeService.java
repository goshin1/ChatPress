package com.example.chatpress.service;

import com.example.chatpress.dto.AgreeDto;
import com.example.chatpress.entity.AgreeEntity;
import com.example.chatpress.repository.AgreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgreeService {

    @Autowired
    private AgreeRepository agreeRepository;

    public String check(AgreeDto dto) {
        AgreeEntity save = agreeRepository.save(dto.toEntity());
        return "ok";

    }
}
