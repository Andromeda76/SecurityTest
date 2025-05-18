package com.example.securitytest.controller;


import com.example.securitytest.model.entity.UserInfo;
import com.example.securitytest.service.model.UserInfoService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    public UserInfo getInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail("ali.asgari1376@gmail.com");
        userInfo.setPassword("asgari");
        userInfo.setUsername("ali");

        setUserInCurrentAuthorizedSession();
//        UserDetails userDetails = userInfoService.save(userInfo);

        return userInfo;
    }



    private void setUserInCurrentAuthorizedSession() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfo userInfo = (UserInfo) authentication.getPrincipal();
        System.out.println(userInfo.getPassword());
    }

}
