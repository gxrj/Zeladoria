package com.femass.authzserver.auth.services;

import java.util.Optional;

import com.femass.authzserver.auth.models.domain.UserEntity;
import com.femass.authzserver.auth.repositories.UserRepository;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    private final UserRepository repository;

    public UserService( UserRepository repository ) {
        this.repository = repository;
    }

    public UserEntity findByUsername( String username ) throws 
            UsernameNotFoundException {

        Optional< UserEntity > user = repository
                                        .findByUsername( username );

        if( user.isEmpty() )
            throw new UsernameNotFoundException( "User not found" );
        
        return user.get();
    }
}