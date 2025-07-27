package com.example.securitytest.controller;


import com.example.securitytest.service.model.AuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/authority")
@RequiredArgsConstructor
public class AuthorityController {

    private final AuthorityService service;


    @GetMapping("/save")
    public String save() {
        return "service.save(authority)";
    }

}
