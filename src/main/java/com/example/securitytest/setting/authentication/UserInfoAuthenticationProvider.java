package com.example.securitytest.setting.authentication;


import com.example.securitytest.service.event.UserInfoAuthToken;
import lombok.RequiredArgsConstructor;

import java.util.List;
import com.example.securitytest.model.entity.UserInfo;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import com.example.securitytest.service.model.UserInfoService;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;



@Component
@RequiredArgsConstructor
public class UserInfoAuthenticationProvider implements AuthenticationProvider {


    private final UserInfoService userInfoService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication instanceof UserInfoAuthToken authenticationToken) {
            String username = authenticationToken.getName(); // or getUserInfo().getUsername()
            String rawPassword = authenticationToken.getCredentials().toString();

            UserInfo userInfo = userInfoService.findByUsername(username).block();

            if (userInfo == null || !userInfo.getPassword().equals(rawPassword)) {
                throw new UsernameNotFoundException("User not found");
            }

            List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
            return new UserInfoAuthToken(userInfo, rawPassword, authorities, userInfo);
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
