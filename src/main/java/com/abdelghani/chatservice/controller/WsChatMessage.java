package com.abdelghani.chatservice.controller;

import com.abdelghani.chatservice.DTO.ConversationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WsChatMessage {

    private Long id;
    private String sender;
    private String recipient;
    private String content;
    private WsChatMessageType type;
    private ConversationDto conversation;
    private String conv;

}
