package com.femass.resourceserver.services;

import java.util.List;
import java.util.Optional;

import com.femass.resourceserver.domain.Citizen;
import com.femass.resourceserver.repositories.CitizenAccountRepository;
import com.femass.resourceserver.repositories.CitizenRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CitizenService {

    @Autowired
    private CitizenRepository citizenRepository;

    @Autowired
    private CitizenAccountRepository accountRepository;

    private final Logger LOG = LoggerFactory.getLogger( CitizenService.class );

    public boolean createOrUpdate( Citizen entity ) {

        try {
            accountRepository.save( entity.getAccount() );
            citizenRepository.save( entity );
            return true;
        }
        catch( IllegalArgumentException ex ) {
            LOG.error( "CitizenService failed: {}", ex.getMessage() );
            return false;
        }
    }

    public boolean createMultiple( List<Citizen> citizens ) {

        try {
            citizenRepository.saveAllAndFlush( citizens );
            return true;
        }
        catch( Exception e ) {
            LOG.error( "CitizenService failed: {}", e.getMessage() );
            return false;
        }
    }

    public void delete( Citizen entity ) throws RuntimeException {
        var account = entity.getAccount();
        account.setEnabled( false );
        try { accountRepository.saveAndFlush( account ); }
        catch( IllegalArgumentException ex ) {
            LOG.error( "CitizenService failed: {}", ex.getMessage() );
            throw new RuntimeException( "CitizenService failed: "+ ex.getMessage() );
        }
    }

    public Citizen findByUsername( String username ) {

        Optional<Citizen> user = citizenRepository
                                        .findByAccount_Username( username );

        return user.isEmpty() ? null : user.get();
    }

    public boolean existsCitizenByUsername( String username ) {
        return citizenRepository.existsByAccount_Username( username );
    }

    public long countUsers() { return citizenRepository.count(); }
}