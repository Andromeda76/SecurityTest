package com.example.securitytest.service.model;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.securitytest.model.entity.Person;
import com.example.securitytest.repository.PersonIRepository;
import org.springframework.security.crypto.password.PasswordEncoder;


@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonIRepository repository;
    private final PasswordEncoder passwordEncoder;


    public Person save(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        return repository.save(person);
    }

}
