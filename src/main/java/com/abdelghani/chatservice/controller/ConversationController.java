package com.abdelghani.chatservice.controller;

import com.abdelghani.chatservice.entities.Conversation;
import com.abdelghani.chatservice.entities.User;
import com.abdelghani.chatservice.repository.UserRepository;
import com.abdelghani.chatservice.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conversations")
@CrossOrigin("*")
public class ConversationController {

    @Autowired
    private ConversationService conversationService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{username}")
    public List<Conversation> getAllConversations(@PathVariable String username) {
        User user = userRepository.findByUsername(username);
        return conversationService.getAllConversationsForUser(user);
    }
}