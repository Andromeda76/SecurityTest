package com.example.securitytest.service.event;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@Component
@RequiredArgsConstructor
public class SecurityContextModelAware implements UserDetailsService {

    /**
     * This implementation of <AuthorityUtils.createAuthorityList(userInfo.getUsername())>
     *     is wrong because we should treat it as a user role provider that makes decision
     *     based on username not have the username itself as role
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
