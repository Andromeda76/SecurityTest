package com.example.securitytest.repository;

import com.example.securitytest.model.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityIRepository extends JpaRepository<Authority, Long> {
}
