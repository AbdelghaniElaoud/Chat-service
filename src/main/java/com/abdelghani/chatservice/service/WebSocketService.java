package com.abdelghani.chatservice.service;

import com.abdelghani.chatservice.controller.WsChatMessageType;
import com.abdelghani.chatservice.entities.Message;
import com.abdelghani.chatservice.entities.User;
import com.abdelghani.chatservice.repository.MessageRepository;
import com.abdelghani.chatservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class WebSocketService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void saveMessage(String senderUsername, String content, WsChatMessageType type) {
        User sender = userRepository.findByUsername(senderUsername);
        Message message = new Message();
        message.setContent(content);
        message.setType(type);
        message.setSender(sender);
        message.setTimestamp(LocalDateTime.now());
        messageRepository.save(message);

        // Broadcast the message to all connected clients
        messagingTemplate.convertAndSend("/topic/public", message);
    }

    public String addUser(String username){
        User userOptional = userRepository.findByUsername(username);
        if (!(userOptional == null)){
            throw new RuntimeException("User already in the databse");
        }
        User user = User.builder()
                .username(username)
                .build();
        userRepository.save(user);
        return username;
    }
}
