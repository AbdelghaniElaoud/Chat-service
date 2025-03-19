package com.abdelghani.chatservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "conversations")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Conversation {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@ManyToOne
@JoinColumn(name = "sender_id")
private User sender;

@ManyToOne
@JoinColumn(name = "receiver_id")
private User receiver;

@Column(nullable = false)
private LocalDateTime createdAt = LocalDateTime.now();

@TableGenerator(name = "conversation_gen")
@Column(nullable = false, unique = true)
private String uniqueIdentifier;

}
