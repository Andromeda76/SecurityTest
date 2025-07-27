package com.example.securitytest.controller;


import com.example.securitytest.model.entity.UserInfo;
import com.example.securitytest.service.model.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RestController
@RequiredArgsConstructor
@RequestMapping("/securityAPI")
public class SecurityAPI {


    private final UserInfoService userInfoService;


    @PostMapping("/saveInfo")
    public Mono<UserInfo> saveInfo(@RequestBody UserInfo userInfo) {
        return null;
    }


    @GetMapping("/getInfo")
    public User getInfo() {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return setUserInCurrentAuthorizedSession();
    }



    private User setUserInCurrentAuthorizedSession() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userInfo = (User) authentication.getPrincipal();
        System.out.println(userInfo.getPassword());
        return userInfo;
    }

}
