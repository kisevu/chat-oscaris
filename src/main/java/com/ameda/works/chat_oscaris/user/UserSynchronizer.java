package com.ameda.works.chat_oscaris.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserSynchronizer {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public void synchronizeWithIdp(Jwt token) {
        log.info("Synchronizing user with idp>>");
        getUserEmail(token).ifPresent( usrEmail -> {
            log.info("Synchronizing user with email{}",usrEmail);
            Optional<User> optUser=  userRepository.findByEmail(usrEmail); //for double-checking purposes (optional)
            User user = userMapper.fromTokenAttributes(token.getClaims());
            /*
            * if user already exists in the database
            * so go ahead and update user with the right id
            *  for double-checking purposes (optional)
            * */
            optUser.ifPresent(value -> user.setId(optUser.get().getId()));
            userRepository.save(user);
        });
    }

    /*
    *  extracting user email from the token
    * */

    private Optional<String> getUserEmail (Jwt token ){
        Map<String, Object> attributes = token.getClaims();

        if( attributes.containsKey("email")){
            return Optional.of(attributes.get("email").toString());
        }

        return Optional.empty();
    }
}
