package com.example.securitytest.setting;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


public class SecurityConfigOld {

//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user = User.builder()
//                .username("username")
//                .password("{noop}password") // رمز عبور باید رمزنگاری شود
//                .roles("USER") // به صورت خودکار به "ROLE_USER" تبدیل می‌شود
//                .build();
//        return new InMemoryUserDetailsManager(user);
//    }

}
