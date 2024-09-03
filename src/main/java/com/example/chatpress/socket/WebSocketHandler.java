package com.example.chatpress.socket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final ChatService chatService;

    private static final ConcurrentHashMap<String, WebSocketSession> CLIENTS = new ConcurrentHashMap<String, WebSocketSession>();

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status){
        CLIENTS.remove(session.getId());
    }


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws JsonProcessingException {
        String payload = message.getPayload();
        System.out.println("payload : "+payload);
        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
        System.out.println(chatMessage.toString());
        ChatRoom chatRoom = chatService.findRoomById(chatMessage.getChatRoomId());

        chatRoom.handleMessage(session, chatMessage,objectMapper);
    }

}

