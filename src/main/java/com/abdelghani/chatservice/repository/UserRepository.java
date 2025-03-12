package com.abdelghani.chatservice.repository;

import com.abdelghani.chatservice.entities.Message;
import com.abdelghani.chatservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}