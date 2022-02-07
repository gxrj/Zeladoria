package com.femass.resourceserver.services;

import java.util.Objects;
import java.util.Optional;

import com.femass.resourceserver.domain.UserEntity;
import com.femass.resourceserver.repositories.UserRepository;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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

    public boolean existsUserByUsername( String username ) {
        return repository.existsByUsername( username );
    }

    public boolean create( UserEntity entity ) {

        Assert.notNull( entity, "Service failed" );
        var result = repository.save( entity );

        if( Objects.isNull( result ) ) return false;

        return true;
    }
}