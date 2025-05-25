package com.example.securitytest.repository;

import com.example.securitytest.model.entity.GroupInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupInfoIRepository extends JpaRepository<GroupInfo, Long> {
}
