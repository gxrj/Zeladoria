package com.femass.resourceserver.services;

import com.femass.resourceserver.domain.ForwardStatus;
import com.femass.resourceserver.domain.ForwardedCall;
import com.femass.resourceserver.repositories.ForwardedCallRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForwardCallService {

    @Autowired
    private ForwardedCallRepository repository;

    private final Logger LOG = LoggerFactory.getLogger( ForwardCallService.class );

    public boolean createOrUpdate( ForwardedCall forwardedCall ) {

        try{
            repository.save( forwardedCall );
            return true;
        }
        catch( IllegalArgumentException ex ){
            LOG.error( "ForwardedCallService failed: {}", ex.getMessage() );
            return false;
        }
    }

    public List<ForwardedCall> findAllByDepartment( String departmentName ) {
        return repository.findAllByDestination_Name( departmentName );
    }

    public List<ForwardedCall> findAllPendingByDepartment( String departmentName ) {
        return repository.findAllByDestination_NameAndStatus( departmentName, ForwardStatus.OPEN );
    }

    public void delete( ForwardedCall forwardedCall ) throws RuntimeException {

        try{ repository.delete( forwardedCall ); }
        catch( IllegalArgumentException ex ){
            LOG.error( "ForwardedCallService failed: {}", ex.getMessage() );
            throw new RuntimeException( "ForwardedCallService failed: "+ ex.getMessage() );
        }
    }
}