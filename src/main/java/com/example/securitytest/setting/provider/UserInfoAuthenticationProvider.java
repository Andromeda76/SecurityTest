package com.example.securitytest.setting.provider;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationProvider;
import java.util.Objects;
import org.springframework.security.core.AuthenticationException;
import com.example.securitytest.service.event.SecurityContextModel;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;



@Component
@RequiredArgsConstructor
public class UserInfoAuthenticationProvider implements AuthenticationProvider {


    private final PasswordEncoder passwordEncoder;
    private final SecurityContextModel securityContextModel;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication instanceof UsernamePasswordAuthenticationToken authenticationToken) {
            String username = authenticationToken.getName();
            String rawPassword = authenticationToken.getCredentials().toString();

            UserDetails userDetails = securityContextModel.loadUserByUsername(username);

            if (Objects.isNull(userDetails) || !passwordEncoder.matches(rawPassword, userDetails.getPassword())) {
                throw new UsernameNotFoundException("User not found");
            }

            return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        }
        throw new AuthenticationServiceException("Unsupported authentication token: " + authentication.getClass());
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }


    /**This line of code in here throws cascading exception:
     * Mono<UserInfo> userInfo = (Mono<UserInfo>) authenticationToken.getPrincipal();
     * because we are in the beginning of the authentication and nothing happened yet that
     * we will be able to have principal instance of our domain;
     * after the method is returned we will have it back;
     */


    /**
     * Spring Security delegates to an AuthenticationProvider object to determine whether a
     * user is authenticated or not. This means we can write custom AuthenticationProvider
     * implementations to inform Spring Security how to authenticate in different ways.
     */

}
