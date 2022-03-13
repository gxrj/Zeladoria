package com.femass.resourceserver.services.accountservices;

import com.femass.resourceserver.domain.account.AgentCredentials;
import com.femass.resourceserver.domain.account.CitizenAccount;
import com.femass.resourceserver.repositories.CitizenAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CitizenAccountService {

    @Autowired
    private CitizenAccountRepository repository;
    private final Logger LOG = LoggerFactory.getLogger( CitizenAccountService.class );

    public boolean createOrUpdate( CitizenAccount entity ) {

        try {
            repository.save( entity );
            return true;
        }
        catch( IllegalArgumentException ex ) {
            LOG.error( "CitizenAccountService failed: {}", ex.getMessage() );
            return false;
        }
    }

    public boolean createMultiple( List<CitizenAccount> accounts ) {
        try{
            repository.saveAllAndFlush( accounts );
            return true;
        }
        catch( Exception e ) {
            LOG.error( "CitizenAccountService failed: {}", e.getMessage() );
            return false;
        }
    }

    public void delete() {

    }

    public CitizenAccount findByUsername(String username ) {

        Optional< CitizenAccount > agent = repository
                .findByUsername( username );

        return agent.isEmpty() ? null : agent.get();
    }

    public boolean existsCitizenByUsername( String username ) {
        return repository.existsByUsername( username );
    }

    public boolean checkCpf( AgentCredentials credentials,
                             AgentCredentials anotherCredentials ) {

        if( credentials == null || anotherCredentials == null ) return false;

        return !credentials.getCpf().equals( anotherCredentials.getCpf() );
    }

    public long countCitizenAccounts() { return repository.count(); }
}
