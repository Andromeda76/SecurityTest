package com.example.securitytest.setting;


import javax.sql.DataSource;

import com.example.securitytest.model.defaults.Query;
import com.example.securitytest.setting.authentication.UserInfoAuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.authentication.AuthenticationManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.example.securitytest.service.event.UserInfoFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;



@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new SCryptPasswordEncoder(32768, 16, 4, 64, 32);
    }


    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager();
        userDetailsManager.setDataSource(dataSource);
        userDetailsManager.setEnableGroups(Boolean.TRUE);
        userDetailsManager.setAuthoritiesByUsernameQuery(Query.CUSTOM_GROUP_QUERY());
        userDetailsManager.setUsersByUsernameQuery(Query.CUSTOM_USER_BY_USERNAME_QUERY());
        userDetailsManager.setGroupAuthoritiesByUsernameQuery(Query.CUSTOM_GROUP_AUTHORITIES_BY_USERNAME_QUERY());

        return userDetailsManager;
    }


    @Bean
    public UserInfoFilter userInfoFilter(AuthenticationManager authenticationManager) {
        UserInfoFilter filter = new UserInfoFilter(authenticationManager);
        var successHandler = new SavedRequestAwareAuthenticationSuccessHandler();

        successHandler.setDefaultTargetUrl("/securityAPI/getInfo");
        successHandler.setAlwaysUseDefaultTargetUrl(Boolean.TRUE);
        filter.setFilterProcessesUrl("/loginAPI/log");
        filter.setUsernameParameter("username");
        filter.setPasswordParameter("password");

        filter.setAuthenticationSuccessHandler(successHandler);
        filter.afterPropertiesSet();
        return filter;
    }

    /**
     * Because we have hikari database config in our properties
     * it has been found by Spring boot and injected automatically in our bean DataSource
     * @return
     */

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsManager manager) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(manager);
        return new ProviderManager(daoAuthenticationProvider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, UserDetailsManager manager) throws Exception {
        http.headers(headers -> headers.
                   frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));

        http.securityContext(securityContextConfigurer ->
                        securityContextConfigurer.requireExplicitSave(Boolean.FALSE))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/loginAPI/log")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .addFilterAt(userInfoFilter(authenticationManager(manager)), UserInfoFilter.class);

        return http.build();
    }

}
