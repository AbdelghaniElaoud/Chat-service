package com.abdelghani.chatservice.service;

import com.abdelghani.chatservice.DTO.ConversationDto;
import com.abdelghani.chatservice.DTO.MessageDTO;
import com.abdelghani.chatservice.controller.WsChatMessageType;
import com.abdelghani.chatservice.entities.Conversation;
import com.abdelghani.chatservice.entities.Message;
import com.abdelghani.chatservice.entities.User;
import com.abdelghani.chatservice.repository.ConversationRepository;
import com.abdelghani.chatservice.repository.MessageRepository;
import com.abdelghani.chatservice.repository.UserRepository;
import com.abdelghani.chatservice.utils.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class WebSocketService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ConversationService conversationService;
    @Autowired
    private ConversationRepository conversationRepository;

    public void saveMessage(ConversationDto conversationDto, String content) {


        MessageDTO message = MessageDTO.builder()
                .content(content)
                .conversation(conversationDto)
                .type("CHAT")
                .build();

        System.out.println("/user/" + conversationDto.getId() + "/messages/");
        messagingTemplate.convertAndSendToUser(conversationDto.getReceiver().getUsername(), "/user/" + conversationDto.getId() + "/messages/", message);
    }

    public String addUser(String username) {
        User userOptional = userRepository.findByUsername(username);
        if (userOptional != null) {
            throw new RuntimeException("User already in the database");
        }
        User user = User.builder()
                .username(username)
                .build();
        userRepository.save(user);
        return username;
    }

    public List<Message> loadMessagesForConversation(Long conversationId) {
        Conversation conversationOptional = conversationRepository.findById(conversationId).get();
        return messageRepository.findByConversation(conversationOptional);
    }
}