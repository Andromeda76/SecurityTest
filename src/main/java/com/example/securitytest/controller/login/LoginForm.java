package com.example.securitytest.controller.login;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginForm {


    @GetMapping("/Login.html")
    public String login() {
        return "Login";
    }

}
