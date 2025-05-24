package com.example.securitytest.service.model;


import org.springframework.context.annotation.Bean;
import com.example.securitytest.repository.UserInfoIRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import com.example.securitytest.model.entity.UserInfo;
import org.springframework.security.core.AuthenticationException;


@Service
@RequiredArgsConstructor
public class UserInfoService {


    private final UserInfoIRepository repository;


    public UserInfo save(UserInfo event) throws AuthenticationException {

         Mono<UserInfo> eventMono = repository.save(event);
//        var auth = new UsernamePasswordAuthenticationToken(eventMono, event.getPassword());

         return event;
    }


    public Mono<UserInfo> findByUsername(String email) {
        return repository.findByUsername(email);
    }

}
