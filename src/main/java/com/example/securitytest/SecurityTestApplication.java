package com.example.securitytest;


import com.example.securitytest.service.model.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@RequiredArgsConstructor
public class SecurityTestApplication {

    private final UserInfoService userInfoService;

    public static void main(String[] args) {
        SpringApplication.run(SecurityTestApplication.class, args);
    }

    @Bean
    public String charSet() {
        char[] ali = new char[30];
        userInfoService.testChar(ali);
        String.valueOf(ali);
        return String.valueOf(ali);
    }

}
