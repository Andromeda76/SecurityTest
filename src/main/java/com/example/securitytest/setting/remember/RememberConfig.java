package com.example.securitytest.setting.remember;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.RememberMeServices;


@Configuration
public class RememberConfig {


    @Bean
    public RememberMeServices rememberMeServices(UserDetailsService userDetailsService) {
        return new CustomizedCookieTokenBuilder(userDetailsService);
    }

}
