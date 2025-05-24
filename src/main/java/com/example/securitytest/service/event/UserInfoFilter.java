package com.example.securitytest.service.event;

import com.example.securitytest.model.entity.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


public class UserInfoFilter extends UsernamePasswordAuthenticationFilter {


    public UserInfoFilter(AuthenticationManager authenticationManager) {
        this.setAuthenticationManager(authenticationManager);
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UserInfo userInfo = new UserInfo();

        String username = obtainUsername(request);
        String password = obtainPassword(request);
        String email = request.getParameter("email");

        userInfo.setUsername(username);
        userInfo.setPassword(password);
        userInfo.setEmail(email);

        UserInfoAuthToken authRequest = new UserInfoAuthToken(username, password, userInfo);
        setDetails(request, authRequest);////??????

        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
