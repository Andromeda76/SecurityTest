package com.example.securitytest.setting.authentication;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationProvider;
import com.example.securitytest.model.entity.Person;
import com.example.securitytest.service.model.PersonService;
import com.example.securitytest.service.event.UserInfoAuthToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.authentication.AuthenticationServiceException;


@Component
@RequiredArgsConstructor
public class UserInfoAuthenticationProvider implements AuthenticationProvider {

    private final PersonService userInfoService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication instanceof UserInfoAuthToken authenticationToken) {
            String username = authenticationToken.getName();
            String rawPassword = authenticationToken.getCredentials().toString();

            Person userInfo = userInfoService.findByUsername(username);

            if (userInfo == null || !passwordEncoder.matches(rawPassword, userInfo.getPassword())) {
                throw new UsernameNotFoundException("User not found");
            }

            return new UserInfoAuthToken(userInfo, rawPassword, List.of(userInfo.getGroupInfo().getAuthority()), userInfo);
        }

        throw new AuthenticationServiceException("Unsupported authentication token: " + authentication.getClass());
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return UserInfoAuthToken.class.isAssignableFrom(authentication);
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
