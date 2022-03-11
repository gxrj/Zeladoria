package com.femass.resourceserver.services.accountservices;

import com.femass.resourceserver.domain.account.AgentAccount;
import com.femass.resourceserver.domain.account.AgentCredentials;
import com.femass.resourceserver.repositories.AgentAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AgentAccountService {

    @Autowired
    private AgentAccountRepository repository;
    private final Logger LOG = LoggerFactory.getLogger( AgentAccountService.class );

    public boolean createOrUpdate( AgentAccount entity ) {

        try {
            repository.save( entity );
            return true;
        }
        catch( IllegalArgumentException ex ) {
            LOG.error( "AgentService failed: {}", ex.getMessage() );
            return false;
        }
    }

    public void delete() {

    }

    public AgentAccount findByUsername( String username ) {

        Optional< AgentAccount > agent = repository
                .findByUsername( username );

        return agent.isEmpty() ? null : agent.get();
    }

    public boolean existsAgentByUsername( String username ) {
        return repository.existsByUsername( username );
    }

    public boolean checkCpf( AgentCredentials credentials,
                             AgentCredentials anotherCredentials ) {

        if( credentials == null || anotherCredentials == null ) return false;

        return !credentials.getCpf().equals( anotherCredentials.getCpf() );
    }

    public long countAgentAccounts() { return repository.count(); }
}