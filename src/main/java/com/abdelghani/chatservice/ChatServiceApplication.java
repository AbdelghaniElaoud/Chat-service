package com.abdelghani.chatservice;

import com.abdelghani.chatservice.entities.User;
import com.abdelghani.chatservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChatServiceApplication implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(ChatServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        User user = User.builder()
                .username("Abdelghani")
                .build();
        User user1 = User.builder()
                .username("Hola")
                .build();
        User user2 = User.builder()
                .username("user")
                .build();
        User user3 = User.builder()
                .username("helo")
                .build();
        userRepository.save(user);
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user);

    }
}
