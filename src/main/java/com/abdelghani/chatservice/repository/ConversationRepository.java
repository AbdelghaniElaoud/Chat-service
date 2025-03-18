package com.abdelghani.chatservice.repository;

import com.abdelghani.chatservice.entities.Conversation;
import com.abdelghani.chatservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    Optional<Conversation> findBySenderAndReceiver(User sender, User receiver);
    @Query("SELECT c FROM Conversation c WHERE c.sender = :user OR c.receiver = :user")
    List<Conversation> findBySenderOrReceiver(@Param("user") User user);
}
