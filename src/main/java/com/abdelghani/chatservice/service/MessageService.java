package com.abdelghani.chatservice.service;

import com.abdelghani.chatservice.DTO.MessageDTO;
import com.abdelghani.chatservice.controller.WsChatMessageType;
import com.abdelghani.chatservice.entities.Conversation;
import com.abdelghani.chatservice.entities.Message;
import com.abdelghani.chatservice.repository.ConversationRepository;
import com.abdelghani.chatservice.repository.MessageRepository;
import com.abdelghani.chatservice.utils.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private ConversationRepository conversationRepository;

//    public Message saveMessage(Message message) {
//        return messageRepository.save(message);
//    }

    public Message saveMessage(String content, Conversation conversation) {
        Message message = new Message();
        message.setContent(content);
        message.setConversation(conversation);
        return messageRepository.save(message);
    }

    public Message saveMessageUsingMessageDTO(MessageDTO messageDTO) {
        Conversation conversation = ObjectMapper.map(messageDTO.getConversation(), Conversation.class);
        Message message = Message.builder()
                .type(WsChatMessageType.CHAT)
                .content(messageDTO.getContent())
                .conversation(conversation)
                .timestamp(LocalDateTime.now())
                .build();
        System.out.println("Save Message is called");
        return messageRepository.save(message);
    }
}
