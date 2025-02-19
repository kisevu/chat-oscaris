package com.ameda.works.chat_oscaris.message;


import com.ameda.works.chat_oscaris.file.Utility;
import org.springframework.stereotype.Service;

@Service
public class MessageMapper {
    public MessageResponse toMessageResponse(Message message) {
        return MessageResponse.builder()
                .id(message.getId())
                .content(message.getContent())
                .senderId(message.getSenderId())
                .receiverId(message.getReceiverId())
                .type(message.getType())
                .state(message.getState())
                .createdAt(message.getCreatedDate())
                .media(Utility.readfile(message.getMediaFilePath()))
                .build();
    }
}
