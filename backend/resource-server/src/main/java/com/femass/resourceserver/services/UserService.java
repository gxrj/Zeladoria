package com.femass.resourceserver.services;

import java.util.Optional;

import com.femass.resourceserver.domain.user.UserEntity;
import com.femass.resourceserver.repositories.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    private final UserRepository repository;
    private final Logger LOG = LoggerFactory.getLogger( UserService.class );

    public UserService( UserRepository repository ) {
        this.repository = repository;
    }


    public boolean createOrUpdate( UserEntity entity ) {

        try {
            repository.save( entity );
            return true;
        }
        catch( IllegalArgumentException ex ) {
            LOG.error( "UserService failed: {}", ex.getMessage() );
            return false;
        }
    }

    public void delete() {

    }

    public UserEntity findByUsername( String username ) {

        Optional< UserEntity > user = repository
                                        .findByUsername( username );

        return user.isEmpty() ? null : user.get();
    }

    public boolean existsUserByUsername( String username ) {
        return repository.existsByUsername( username );
    }

    public long countUsers() { return repository.count(); }
}