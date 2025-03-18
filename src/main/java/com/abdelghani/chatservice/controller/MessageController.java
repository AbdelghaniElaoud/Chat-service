package com.abdelghani.chatservice.controller;

import com.abdelghani.chatservice.entities.Conversation;
import com.abdelghani.chatservice.entities.Message;
import com.abdelghani.chatservice.entities.User;
import com.abdelghani.chatservice.repository.ConversationRepository;
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
    private ConversationRepository conversationRepository;

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

    @GetMapping("/conversation/{conversationId}")
    public List<Message> getMessagesByConversationId(@PathVariable Long conversationId) {
        return messageRepository.findByConversation(conversationRepository.findById(conversationId).get());
    }

    @PostMapping
    public Message createMessage(@RequestBody Message message) {
        return messageService.saveMessage(message.getContent(), message.getConversation());
    }

    @PostMapping("/conversation")
    public Message createMessageForConversation(@RequestBody Message message) {
        User sender = userRepository.findByUsername(message.getConversation().getSender().getUsername());
        User receiver = userRepository.findByUsername(message.getConversation().getReceiver().getUsername());
        Long conversationId = conversationRepository.findBySenderAndReceiver(sender, receiver)
                .map(Conversation::getId)
                .orElseGet(() -> {
                    Conversation newConversation = new Conversation();
                    newConversation.setSender(sender);
                    newConversation.setReceiver(receiver);
                    return conversationRepository.save(newConversation).getId();
                });

        message.setConversation(conversationRepository.findById(conversationId).get());
        return messageService.saveMessage(message.getContent(), message.getConversation());
    }
}