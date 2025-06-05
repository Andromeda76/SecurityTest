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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;


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

        http.cors(cors -> {
            cors.configurationSource(corsConfigurationSource());
            });

        http.rememberMe(httpSecurityRememberMeConfigurer ->
                    httpSecurityRememberMeConfigurer.key("remember-me"));

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


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        return s -> {
            CorsConfiguration corsConfiguration = new CorsConfiguration();
            corsConfiguration.setAllowedOrigins(List.of("*","*"));
            corsConfiguration.addAllowedOrigin("*");
            corsConfiguration.addAllowedHeader("*");
            corsConfiguration.addAllowedMethod("*");
            corsConfiguration.addExposedHeader("Authorization");
            corsConfiguration.addExposedHeader("Access-Control-Allow-Origin");
            corsConfiguration.addExposedHeader("Access-Control-Allow-Methods");
            corsConfiguration.validateAllowCredentials();
            corsConfiguration.setMaxAge(Duration.of(30, ChronoUnit.MICROS));
            corsConfiguration.setAllowPrivateNetwork(Boolean.TRUE);
            corsConfiguration.setAllowCredentials(Boolean.TRUE);
            corsConfiguration.validateAllowPrivateNetwork();
            return corsConfiguration;
        };
    }

}
