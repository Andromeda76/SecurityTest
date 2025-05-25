package com.example.securitytest.controller;


import com.example.securitytest.model.entity.GroupInfo;
import com.example.securitytest.service.model.GroupInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/groupInfo")
@RequiredArgsConstructor
public class GroupInfoController {

    private final GroupInfoService service;


    @PostMapping("/save")
    public GroupInfo save(@RequestBody GroupInfo groupInfo) {
        return service.save(groupInfo);
    }
}
