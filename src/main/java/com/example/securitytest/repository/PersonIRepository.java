package com.example.securitytest.repository;

import com.example.securitytest.model.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PersonIRepository extends JpaRepository<Person, Long> {
}
