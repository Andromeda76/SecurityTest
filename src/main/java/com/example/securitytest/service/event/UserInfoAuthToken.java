package com.example.securitytest.service.event;


import java.util.Collection;

import com.example.securitytest.model.entity.Person;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


@Getter
public final class UserInfoAuthToken extends UsernamePasswordAuthenticationToken {


    private final Person userInfo;


    public UserInfoAuthToken(Object principal, Object credentials, Person userInfo) {
        super(principal, credentials);
        this.userInfo = userInfo;
    }

    public UserInfoAuthToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities, Person userInfo) {
        super(principal, credentials, authorities);
        this.userInfo = userInfo;
    }

}
