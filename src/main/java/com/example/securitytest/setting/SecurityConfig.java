package com.example.securitytest.setting;


import com.example.securitytest.setting.authentication.UserInfoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.web.SecurityFilterChain;
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

    private final UserInfoAuthenticationProvider userInfoAuthenticationProvider;

    /**
     * Because we have hikari database config in our properties
     * it has been found by Spring boot and injected automatically in our bean DataSource
     * @return
     */

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

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.authenticationProvider(userInfoAuthenticationProvider);
        return builder.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
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
                .addFilterAt(userInfoFilter(authenticationManager(http)), UserInfoFilter.class);

        return http.build();
    }

}
