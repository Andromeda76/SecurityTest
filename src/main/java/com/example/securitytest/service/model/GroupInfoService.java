package com.example.securitytest.service.model;


import com.example.securitytest.model.entity.GroupInfo;
import com.example.securitytest.repository.GroupInfoIRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class GroupInfoService {

    private final GroupInfoIRepository repository;

    public GroupInfo save(GroupInfo groupInfo) {
        return repository.save(groupInfo);
    }
}
