package com.ameda.works.chat_oscaris.interceptor;


import com.ameda.works.chat_oscaris.user.UserSynchronizer;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

/*
*  synchronizing keycloak users
* */

@Component
@RequiredArgsConstructor
public class UserSynchronizerFilter extends OncePerRequestFilter {

    private final UserSynchronizer userSynchronizer;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        if(!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken )){
            /*
            *  Always will have then authentication token present
            *  inorder to proceed.
            * */
            JwtAuthenticationToken token = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            /*
            *  here we need everything in the token that will synchronize the user
            *  And here we are synchronizing our users with the IDP ( Identity Provider)
            *  as below
            * */
            userSynchronizer.synchronizeWithIdp(token.getToken());
        }
        filterChain.doFilter(request,response);
    }
}
