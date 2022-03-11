package com.femass.resourceserver.services;

import java.util.Optional;

import com.femass.resourceserver.domain.account.AgentCredentials;
import com.femass.resourceserver.domain.Agent;
import com.femass.resourceserver.repositories.AgentAccountRepository;
import com.femass.resourceserver.repositories.AgentRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgentService {

    @Autowired
    private AgentRepository agentRepository;
    @Autowired
    private AgentAccountRepository accountRepository;
    private final Logger LOG = LoggerFactory.getLogger( AgentService.class );

    public boolean createOrUpdate( Agent entity ) {

        try {
            accountRepository.save( entity.getAccount() );
            agentRepository.save( entity );
            return true;
        }
        catch( IllegalArgumentException ex ) {
            LOG.error( "AgentService failed: {}", ex.getMessage() );
            return false;
        }
    }

    public void delete() {

    }

    public Agent findByUsername( String username ) {

        Optional< Agent > agent = agentRepository
                                            .findByAccount_Username( username );

        return agent.isEmpty() ? null : agent.get();
    }

    public boolean existsAgentByUsername( String username ) {
        return agentRepository.existsByAccount_Username( username );
    }

    public boolean checkCpf( AgentCredentials credentials, 
                                    AgentCredentials anotherCredentials ) {

        if( credentials == null || anotherCredentials == null ) return false;

        return !credentials.getCpf().equals( anotherCredentials.getCpf() );
    }

    public long countAgents() { return agentRepository.count(); }
}