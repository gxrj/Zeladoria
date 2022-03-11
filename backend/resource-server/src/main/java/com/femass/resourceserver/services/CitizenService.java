package com.femass.resourceserver.services;

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
            LOG.error( "UserService failed: {}", ex.getMessage() );
            return false;
        }
    }

    public void delete() {

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