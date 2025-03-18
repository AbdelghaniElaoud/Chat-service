package com.abdelghani.chatservice.controller;

import com.abdelghani.chatservice.entities.User;
import com.abdelghani.chatservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("{username}")
    public User getByUsername(@PathVariable String username) {
        return userRepository.findByUsername(username);
    }
}
