package com.ameda.works.chat_oscaris.chat;

import com.ameda.works.chat_oscaris.user.User;
import com.ameda.works.chat_oscaris.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final ChatMapper chatMapper;
    private  final UserRepository userRepository;


    @Transactional(readOnly = true)
    public List<ChatResponse> getChatsByReceiverId(Authentication currentUser){
        final String userId =  currentUser.getName();
        return chatRepository.findChatsBySenderId(userId).stream()
                .map(c -> chatMapper.toChatMapper(c,userId))
                .toList();
    }

    public String createChat(String senderId, String receiverId ){
        Optional<Chat> existingChat = chatRepository.findChatByReceiverAndSender(senderId,receiverId);
        if ( existingChat.isPresent() ){
            return existingChat.get().getId();
        }
        User sender = userRepository.findByPublicId(senderId)
                .orElseThrow(()-> new EntityNotFoundException("could not find the sender with ID: "+ senderId));
        User receiver = userRepository.findByPublicId(receiverId)
                .orElseThrow(()-> new EntityNotFoundException("could not find the receiver with ID: "+ receiverId));

        /*
        *  till this point we have both participants saved in our dB so proceed
        * */

        Chat chat = new Chat();
        chat.setSender(sender);
        chat.setRecipient(receiver);
        Chat savedChat = chatRepository.save(chat);
        return savedChat.getId();
    }
}
