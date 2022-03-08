package com.femass.authzserver.auth.services;

import java.util.Optional;

import com.femass.authzserver.auth.models.domain.AgentCredentials;
import com.femass.authzserver.auth.models.domain.AgentEntity;
import com.femass.authzserver.auth.repositories.AgentRepository;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AgentService {
    
    private final AgentRepository repository;

    public AgentService( AgentRepository repository ) {
        this.repository = repository;
    }

    public AgentEntity findByUsername( String username ) throws 
            UsernameNotFoundException {

        Optional< AgentEntity > agent = repository
                                            .findByUsername( username );

        if( agent.isEmpty() )
            throw new UsernameNotFoundException( "User not found" );

        return agent.get();
    }

    public boolean checkCpf( AgentCredentials credentials, 
                                    AgentCredentials anotherCredentials ) {

        if( credentials == null || anotherCredentials == null ) return false;

        else if( !credentials.getCpf().equals( anotherCredentials.getCpf() ) ) return false;

        else return true;
    }
}
