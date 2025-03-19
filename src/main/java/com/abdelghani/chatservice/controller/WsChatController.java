package com.abdelghani.chatservice.controller;

import com.abdelghani.chatservice.controller.WsChatMessage;
import com.abdelghani.chatservice.controller.WsChatMessageType;
import com.abdelghani.chatservice.entities.User;
import com.abdelghani.chatservice.repository.UserRepository;
import com.abdelghani.chatservice.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
public class WsChatController {

    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    private UserRepository userRepository;

    @MessageMapping("/chat.sendMessage")
    @SendToUser("/messages")
    public ResponseEntity<WsChatMessage> sendMessage(@Payload WsChatMessage msg, SimpMessageHeaderAccessor headerAccessor) {
        System.out.println("Message received from " + msg.getSender() + ": " + msg.getContent());


        System.out.println("The conversation Id is" + msg.getConversation().getId());
        webSocketService.saveMessage(msg.getConversation(), msg.getContent());

        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/chat")
    public WsChatMessage addUser(@Payload WsChatMessage msg, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", msg.getSender());
        System.out.println("User joined: " + msg.getSender());

        webSocketService.addUser(msg.getSender());
        if (msg.getType() == WsChatMessageType.JOIN) {
            webSocketService.saveMessage(msg.getConversation(), msg.getContent());
        }

        return msg;
    }
}