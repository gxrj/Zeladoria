package com.femass.authzserver.auth.services;

import java.util.Optional;

import com.femass.authzserver.auth.models.domain.CitizenAccount;
import com.femass.authzserver.auth.repositories.CitizenRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CitizenService {

    @Autowired
    private CitizenRepository repository;

    public CitizenAccount findByUsername( String username ) throws
            UsernameNotFoundException {

        Optional<CitizenAccount> account = repository
                                        .findByUsername( username );

        if( account.isEmpty() )
            throw new UsernameNotFoundException( "Account not found" );
        
        return account.get();
    }
}