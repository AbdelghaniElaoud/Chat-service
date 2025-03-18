package com.abdelghani.chatservice.service;

import com.abdelghani.chatservice.entities.Conversation;
import com.abdelghani.chatservice.entities.Message;
import com.abdelghani.chatservice.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

//    public Message saveMessage(Message message) {
//        return messageRepository.save(message);
//    }

    public Message saveMessage(String content, Conversation conversation) {
        Message message = new Message();
        message.setContent(content);
        message.setConversation(conversation);
        return messageRepository.save(message);
    }
}
