package com.femass.authzserver.auth.services;

import java.util.Optional;

import com.femass.authzserver.auth.models.domain.AgentAccount;
import com.femass.authzserver.auth.models.domain.AgentCredentials;
import com.femass.authzserver.auth.repositories.AgentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AgentService {

    @Autowired
    private AgentRepository repository;

    public AgentAccount findByUsername( String username ) throws
            UsernameNotFoundException {

        Optional<AgentAccount> account = repository
                                            .findByUsername( username );

        if( account.isEmpty() )
            throw new UsernameNotFoundException( "Account not found" );

        return account.get();
    }

    public boolean checkCpf( AgentCredentials credentials, 
                                    AgentCredentials anotherCredentials ) {

        if( credentials == null || anotherCredentials == null ) return false;

        else if( !credentials.getCpf().equals( anotherCredentials.getCpf() ) ) return false;

        else return true;
    }
}
