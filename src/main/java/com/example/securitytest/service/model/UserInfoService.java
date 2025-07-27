package com.example.securitytest.service.model;


import com.example.securitytest.model.entity.UserInfo;
import com.example.securitytest.repository.UserInfoIRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Arrays;


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


    public void testChar(char[] chars) {
        char[] newChars = new char[chars.length];
        try {
            newChars = "Password".toCharArray();
            System.arraycopy(newChars, 0, chars, 0, newChars.length);
        } finally {
            Arrays.fill(newChars, '\0');
            System.out.println(Arrays.toString(newChars));
        }

    }

}
