package com.abdelghani.chatservice.controller;

import com.abdelghani.chatservice.entities.Message;
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
import org.springframework.stereotype.Controller;

@Controller
public class WsChatController {

    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    private UserRepository userRepository;

    @MessageMapping("/chat.sendMessage")  // Maps messages sent to "chat.sendMessage" WebSocket destination
    @SendTo("/topic/public")  // Specifies that the return message will be sent to "/topic/public"
    public ResponseEntity<WsChatMessage> sendMessage(@Payload WsChatMessage msg, SimpMessageHeaderAccessor headerAccessor) {
        // Log the sender and content of the message for debugging
        System.out.println("Message received from " + msg.getSender() + ": " + msg.getContent());

        // Retrieve the recipient user from the header or session
        User recipient = userRepository.findByUsername(msg.getRecipient());

        // Save the message and send it to the recipient
        webSocketService.saveMessage(msg.getSender(), msg.getContent(), WsChatMessageType.CHAT, recipient);

        // Broadcast the message to all subscribers on the "/topic/public" topic
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @MessageMapping("/chat.addUser")  // Maps messages sent to "chat.addUser" WebSocket destination
    @SendTo("/topic/chat")  // Specifies that the return message will be sent to "/topic/chat"
    public WsChatMessage addUser(@Payload WsChatMessage msg, SimpMessageHeaderAccessor headerAccessor) {
        // Store the username in the WebSocket session attributes
        headerAccessor.getSessionAttributes().put("username", msg.getSender());

        // Log when a user joins the chat
        System.out.println("User joined: " + msg.getSender());

        webSocketService.addUser(msg.getSender());
        if (msg.getType() == WsChatMessageType.JOIN) {
            // When a user joins, send a system message to the chat
            webSocketService.saveMessage(msg.getSender(), msg.getSender() + " joined", WsChatMessageType.JOIN, null);
        }

        // Broadcast the user join event to all subscribers on the "/topic/chat" topic
        return msg;
    }
}