package com.abdelghani.chatservice.DTO;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
    private String content;
    private String type;
    private ConversationDto conversation;
}
