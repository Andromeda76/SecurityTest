package com.example.securitytest.service.event;


import java.util.Collection;

import com.example.securitytest.model.entity.UserInfo;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


@Getter
public final class UserInfoAuthToken extends UsernamePasswordAuthenticationToken {


    private final UserInfo userInfo;


    public UserInfoAuthToken(Object principal, Object credentials, UserInfo userInfo) {
        super(principal, credentials);
        this.userInfo = userInfo;
    }

    public UserInfoAuthToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities, UserInfo userInfo) {
        super(principal, credentials, authorities);
        this.userInfo = userInfo;
    }

}
