package com.example.securitytest.service.model;


import com.example.securitytest.model.entity.Authority;
import com.example.securitytest.repository.AuthorityIRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthorityService {

    private final AuthorityIRepository repository;


    public Authority save(Authority authority) {
        return repository.save(authority);
    }

}
