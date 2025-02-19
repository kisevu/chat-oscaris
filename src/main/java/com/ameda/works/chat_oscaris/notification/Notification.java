package com.ameda.works.chat_oscaris.notification;


import com.ameda.works.chat_oscaris.message.MessageType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notification {

    private String chatId;
    private String content;
    private String senderId;
    private String receiverId;
    private String chatName;
    private MessageType messageType;
    private NotificationType notificationType;
    private  byte [] media;
}
