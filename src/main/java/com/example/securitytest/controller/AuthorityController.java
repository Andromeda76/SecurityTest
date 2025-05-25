package com.example.securitytest.controller;


import com.example.securitytest.model.entity.Authority;
import com.example.securitytest.service.model.AuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authority")
@RequiredArgsConstructor
public class AuthorityController {

    private final AuthorityService service;


    @PostMapping("/save")
    public Authority save(@RequestBody Authority authority) {
        return service.save(authority);
    }

}
