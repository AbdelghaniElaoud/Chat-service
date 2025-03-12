package com.abdelghani.chatservice.controller;

import com.abdelghani.chatservice.entities.Message;
import com.abdelghani.chatservice.entities.User;
import com.abdelghani.chatservice.repository.MessageRepository;
import com.abdelghani.chatservice.repository.UserRepository;
import com.abdelghani.chatservice.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin("*")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageService messageService;

    @GetMapping
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    @GetMapping("/contacts")
    public List<User> getAllContacts() {
        return userRepository.findAll();
    }

    @GetMapping("/{userId1}/{userId2}")
    public List<Message> getMessagesBetweenUsers(@PathVariable Long userId1, @PathVariable Long userId2) {
        User user1 = userRepository.findById(userId1).orElseThrow(() -> new RuntimeException("User not found"));
        User user2 = userRepository.findById(userId2).orElseThrow(() -> new RuntimeException("User not found"));
        return messageRepository.findBySenderAndRecipientOrRecipientAndSender(user1, user2, user2, user1);
    }

    @PostMapping
    public Message createMessage(@RequestBody Message message) {
        return messageService.saveMessage(message);
    }

}