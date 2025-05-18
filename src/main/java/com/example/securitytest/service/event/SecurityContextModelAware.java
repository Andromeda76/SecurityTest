package com.example.securitytest.service.event;


import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.example.securitytest.model.entity.UserInfo;
import com.example.securitytest.service.model.UserInfoService;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import reactor.core.publisher.Mono;


@Component
@RequiredArgsConstructor
public class SecurityContextModelAware implements UserDetailsService {


    private final UserInfoService userInfoService;

    /**
     * This implementation of <AuthorityUtils.createAuthorityList(userInfo.getUsername())>
     *     is wrong because we should treat it as a user role provider that makes decision
     *     based on username not have the username itself as role
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        Mono<UserInfo> userInfo = userInfoService.findByUsername(username);
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.createAuthorityList("USER_ROLE");

        return null;
    }
}
