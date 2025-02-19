package com.ameda.works.chat_oscaris.user;


import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class UserMapper {


    public User fromTokenAttributes(Map<String, Object> attributes ) {
        User user = new User();

        /*
        * checking subject, user unique identifier
        * */
        if (attributes.containsKey("sub")){
            user.setId(attributes.get("sub").toString());
        }

        /*
         * checking given_name ,to be  the first name
         * or the nickname to be set for the first name in the case of an external identity provider
         * for the case when we use gmail login / GitHub
         * */

        if(attributes.containsKey("given_name")){
            user.setFirstName(attributes.get("given_name").toString());
        } else if (attributes.containsKey("nickname")) {
            user.setFirstName(attributes.get("nickname").toString());
        }

        /*
         * checking family_name ,to be  the last name
         * */

        if(attributes.containsKey("family_name")){
            user.setLastName(attributes.get("family_name").toString());
        }

        /*
         * checking for email ,to be set for email
         * */
        if(attributes.containsKey("email")){
            user.setEmail(attributes.get("email").toString());
        }

        user.setLastSeen(LocalDateTime.now());
        return user;
    }

    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .lastSeen(user.getLastSeen())
                .isOnline(user.isUserOnline())
                .build();
    }
}
