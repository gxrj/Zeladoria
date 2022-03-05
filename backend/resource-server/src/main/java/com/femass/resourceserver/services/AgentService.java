package com.femass.resourceserver.services;

import java.util.Optional;

import com.femass.resourceserver.domain.user.AgentCredentials;
import com.femass.resourceserver.domain.user.AgentEntity;
import com.femass.resourceserver.repositories.AgentRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AgentService {
    
    private final AgentRepository repository;
    private Logger LOG = LoggerFactory.getLogger( AgentService.class );

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

    public boolean existsAgentByUsername( String username ) {
        return repository.existsByAgentCredentials_Username( username );
    }

    public boolean checkCpf( AgentCredentials credentials, 
                                    AgentCredentials anotherCredentials ) {

        if( credentials.isNull() || anotherCredentials.isNull() ) return false;

        return !credentials.getCpf().equals( anotherCredentials.getCpf() );
    }

    public boolean create( AgentEntity entity ) {

        try {
            repository.save( entity );
            return true;
        }
        catch( IllegalArgumentException ex ) {
            LOG.error( "AgentService failed: {}", ex.getMessage() );
            return false;
        }
    }
}