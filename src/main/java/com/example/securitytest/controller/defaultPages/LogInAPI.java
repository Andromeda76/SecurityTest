package com.example.securitytest.controller.defaultPages;


import com.example.securitytest.model.entity.UserInfo;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/loginAPI")
public class LogInAPI {



    @PostMapping("/log")
    public String log(@RequestBody UserInfo userInfo) {
        return "Hello World from logIn";
    }
}
