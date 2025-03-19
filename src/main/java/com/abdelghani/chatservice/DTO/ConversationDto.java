package com.abdelghani.chatservice.DTO;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConversationDto {
    private Long id;

    private UserDto sender;

    private UserDto receiver;

    private LocalDateTime createdAt;
}
