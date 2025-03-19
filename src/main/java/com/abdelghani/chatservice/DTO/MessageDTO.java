package com.abdelghani.chatservice.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDTO {
    private String content;
    private String type;
    private Long conversationId;
}
