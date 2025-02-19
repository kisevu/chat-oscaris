package com.ameda.works.chat_oscaris.message;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class MessageRequest {
    private String content;
    private String senderId;
    private String receiverId;
    private MessageType messageType;
    private String chatId;

}
