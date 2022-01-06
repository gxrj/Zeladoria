package com.femass.resourceserver.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername( String username ) throws UsernameNotFoundException {
        try{

        }catch( UsernameNotFoundException ex ){

        }

        return User.builder()
                    .username( username )
                    .password( "" )
                    .roles()
                    .authorities( "" )
                    .disabled( false )
                    .build();
    }
}