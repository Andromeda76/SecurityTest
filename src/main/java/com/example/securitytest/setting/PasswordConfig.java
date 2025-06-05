package com.example.securitytest.setting;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;


@Configuration
public class PasswordConfig {


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new SCryptPasswordEncoder(32768, 16, 4, 64, 32);
    }

}
