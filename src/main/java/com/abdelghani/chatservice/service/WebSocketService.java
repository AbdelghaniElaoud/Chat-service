package com.abdelghani.chatservice.service;

import com.abdelghani.chatservice.controller.WsChatMessageType;
import com.abdelghani.chatservice.entities.Conversation;
import com.abdelghani.chatservice.entities.Message;
import com.abdelghani.chatservice.entities.User;
import com.abdelghani.chatservice.repository.ConversationRepository;
import com.abdelghani.chatservice.repository.MessageRepository;
import com.abdelghani.chatservice.repository.UserRepository;
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

    public void saveMessage(String senderUsername, String receiverUsername, String content) {
        User sender = userRepository.findByUsername(senderUsername);
        User receiver = userRepository.findByUsername(receiverUsername);
        Conversation conversation = conversationService.getOrCreateConversation(sender, receiver);
        Message message = new Message();
        message.setContent(content);
        message.setConversation(conversation);
        messageRepository.save(message);

        messagingTemplate.convertAndSendToUser(receiverUsername, "/user/" + receiverUsername + "/messages/" + conversation.getId(), message);
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