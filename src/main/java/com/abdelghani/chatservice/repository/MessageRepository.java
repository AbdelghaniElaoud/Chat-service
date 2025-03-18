package com.abdelghani.chatservice.repository;

import com.abdelghani.chatservice.entities.Conversation;
import com.abdelghani.chatservice.entities.Message;
import com.abdelghani.chatservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
//    @Query("SELECT m FROM Message m WHERE (m.sender = :sender AND m.recipient = :recipient) OR (m.sender = :recipient2 AND m.recipient = :sender2)")
//    List<Message> findBySenderAndRecipientOrRecipientAndSender(User sender, User recipient, User recipient2, User sender2);

    @Query("SELECT m FROM Message m WHERE m.conversation = :conversation")
    List<Message> findByConversation(Conversation conversation);
}
