package com.femass.resourceserver.services;

import java.util.Objects;
import java.util.Optional;

import com.femass.resourceserver.domain.AgentCredentials;
import com.femass.resourceserver.domain.AgentEntity;
import com.femass.resourceserver.repositories.AgentRepository;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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

    public boolean existsAgentByUsername( String username ) {
        return repository.existsByUsername( username );
    }

    public boolean checkCpf( AgentCredentials credentials, 
                                    AgentCredentials anotherCredentials ) {

        if( credentials.isNull() || anotherCredentials.isNull() ) return false;

        else if( !credentials.getCpf().equals( anotherCredentials.getCpf() ) ) return false;

        else return true;
    }

    public boolean create( AgentEntity entity ) {

        Assert.notNull( entity, "Service failed" );
        var result = repository.save( entity );

        if( Objects.isNull( result ) ) return false;

        return true;
    }
}