package com.example.securitytest.setting;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.example.securitytest.service.event.UserInfoFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import com.example.securitytest.setting.authentication.UserInfoAuthenticationProvider;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.AuthenticationManager;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserInfoAuthenticationProvider userInfoAuthenticationProvider;

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.authenticationProvider(userInfoAuthenticationProvider);
        return builder.build();
    }

    @Bean
    public UserInfoFilter userInfoFilter(AuthenticationManager authenticationManager) {
        UserInfoFilter filter = new UserInfoFilter(authenticationManager);
        filter.setFilterProcessesUrl("/loginAPI/log");
        filter.setUsernameParameter("username");
        filter.setPasswordParameter("password");

        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setDefaultTargetUrl("/securityAPI/getInfo");

        successHandler.setAlwaysUseDefaultTargetUrl(true);
        filter.setAuthenticationSuccessHandler(successHandler);
        filter.afterPropertiesSet();
        return filter;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.headers(headers -> headers.
                frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));

        http.securityContext(securityContextConfigurer ->
                        securityContextConfigurer.requireExplicitSave(false))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/loginAPI/log")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .addFilterAt(userInfoFilter(authenticationManager(http)), UserInfoFilter.class);

        return http.build();
    }

}
