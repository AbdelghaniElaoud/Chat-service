package com.abdelghani.chatservice.service;

import com.abdelghani.chatservice.entities.Conversation;
import com.abdelghani.chatservice.entities.User;
import com.abdelghani.chatservice.repository.ConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConversationService {

    @Autowired
    private ConversationRepository conversationRepository;


    public List<Conversation> getAllConversationsForUser(User user) {
        return conversationRepository.findBySenderOrReceiver(user);
    }
}
