package com.example.securitytest.setting;


import com.example.securitytest.setting.provider.UserInfoAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.authentication.*;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.x509.X509AuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final RememberMeServices rememberMeServices;
    private final UserDetailsService userDetailsService;
    private final UserInfoAuthenticationProvider userInfoAuthenticationProvider;


    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.authenticationProvider(userInfoAuthenticationProvider);
        return builder.build();
    }


    @Bean
    public UsernamePasswordAuthenticationFilter userInfoFilter(AuthenticationManager authenticationManager) {
        var filter = new UsernamePasswordAuthenticationFilter(authenticationManager);
        var successHandler = new SavedRequestAwareAuthenticationSuccessHandler();

        successHandler.setDefaultTargetUrl("/securityAPI/getInfo");
        filter.setFilterProcessesUrl("/loginAPI/log");
        filter.setUsernameParameter("username");
        filter.setPasswordParameter("password");

        filter.setAuthenticationSuccessHandler(successHandler);
        filter.setRememberMeServices(rememberMeServices);
        filter.afterPropertiesSet();
        return filter;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.headers(headers ->
                headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));

        http.sessionManagement().invalidSessionUrl("/loginAPI/logout");

        x509Authentication(http);
        rememberMeServices(http);
        corsConfigurationSourceBuilder(http);

        http.securityContext(securityContextConfigurer ->
                        securityContextConfigurer.requireExplicitSave(Boolean.FALSE))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/Login.html", "/loginAPI/login")
                        .permitAll()
                            .requestMatchers("/authority/save")
                        .access(new WebExpressionAuthorizationManager(
                                "isFullyAuthenticated() and !isRememberMe() and hasAuthority('ADMIN')"))
                        .anyRequest()
                        .authenticated())
                     .addFilterAt(userInfoFilter(authenticationManager(http)), UsernamePasswordAuthenticationFilter.class)

                     .addFilterAt(new X509AuthenticationFilter(){{
                         setAuthenticationManager(authenticationManager(http));}}, AbstractPreAuthenticatedProcessingFilter.class)

                             .exceptionHandling(ex -> {
                                 ex.authenticationEntryPoint(
                                         new LoginUrlAuthenticationEntryPoint("/Login.html"));//For not authenticated users
                                ex.accessDeniedPage("/error/fuckingDenied"); //For authenticated but unauthorized requests
                             });
        return http.build();
    }


    private void x509Authentication(HttpSecurity http) throws Exception {
        http.x509(x509Builder -> {
            x509Builder.subjectPrincipalRegex("CN=(.*?)(?:,|$)");
            x509Builder.userDetailsService(userDetailsService);
        });
    }


    private void rememberMeServices(HttpSecurity http) throws Exception {
        http.rememberMe(
                rememberMeConfigurer ->
                        rememberMeConfigurer.rememberMeServices(rememberMeServices)
                                .useSecureCookie(Boolean.TRUE)
                                .tokenValiditySeconds(1200));
    }


    private void corsConfigurationSourceBuilder(HttpSecurity http) throws Exception {
        /**
         * 👍👍👍 Make Remember-me cookie path limited 😂😂😂
         */

        http.cors(cors -> {

            cors.configurationSource(configurationSource -> {
                CorsConfiguration corsConfiguration = new CorsConfiguration();

                corsConfiguration.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Requested-With"));
                corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                corsConfiguration.setAllowedOrigins(List.of("http://localhost:63342"));
                corsConfiguration.setMaxAge(Duration.of(30, ChronoUnit.MINUTES));
                corsConfiguration.setAllowCredentials(Boolean.TRUE);

                return corsConfiguration;});
            });
        }

}
