package com.example.securitytest.controller;

import com.example.securitytest.model.entity.Person;
import com.example.securitytest.service.model.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService service;

    @PostMapping("/save")
    public Person save(@RequestBody Person person) {
        return service.save(person);
    }

}
