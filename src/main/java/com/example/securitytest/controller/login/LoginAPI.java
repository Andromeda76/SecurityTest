package com.example.securitytest.controller.login;


import com.example.securitytest.model.entity.UserInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loginAPI")
public class LoginAPI {


    @PostMapping("/log")
    public String login(@RequestBody UserInfo userInfo) {
        return "Success";
    }
}
