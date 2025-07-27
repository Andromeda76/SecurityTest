package com.example.securitytest.service.event;


import com.example.securitytest.model.entity.Person;
import com.example.securitytest.service.model.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class SecurityContextModelAware implements UserDetailsService {


    private final PersonService personService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personService.findByUsername(username);
        return new User(person.getUsername(), person.getPassword(), List.of(person.getGroupInfo().getAuthority()));
    }

    /**
     * This implementation of <AuthorityUtils.createAuthorityList(userInfo.getUsername())>
     *     is wrong because we should treat it as a user role provider that makes decision
     *     based on username not have the username itself as role
     */

}
