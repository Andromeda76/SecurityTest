package com.example.securitytest.service.model;


import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import com.example.securitytest.repository.UserInfoIRepository;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import com.example.securitytest.model.entity.UserInfo;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


@Service
@RequiredArgsConstructor
public class UserInfoService {


    private final UserInfoIRepository repository;


    public UserDetails save(UserInfo event) throws AuthenticationException {

        /**
         * Need asynchronous programming for getting email to fulfill UserInfo
         * data to persist; the email should be received from user web Info
         */

        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.createAuthorityList("ROLE_USER");
        UserDetails user = new User(event.getUsername(), event.getPassword(), grantedAuthorities);

        event.setEmail(event.getEmail());
        event.setUsername(event.getUsername());
        event.setPassword(event.getPassword());

         Mono<UserInfo> eventMono = repository.save(event);

        var auth = new UsernamePasswordAuthenticationToken(eventMono, user.getPassword(), user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

         return user;
    }


    public Mono<UserInfo> findByUsername(String email) {
        return repository.findByUsername(email);
    }

}
