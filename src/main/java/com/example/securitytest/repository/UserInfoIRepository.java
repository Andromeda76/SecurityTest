package com.example.securitytest.repository;


import com.example.securitytest.model.entity.UserInfo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;


public interface UserInfoIRepository extends ReactiveMongoRepository<UserInfo, String> {


    Mono<UserInfo> findByUsername(String username);
    Mono<UserInfo> findByUsernameAndPassword(String username, String password);


}
