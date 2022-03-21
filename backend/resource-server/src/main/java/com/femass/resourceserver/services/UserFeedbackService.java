package com.femass.resourceserver.services;

import com.femass.resourceserver.domain.UserFeedback;
import com.femass.resourceserver.repositories.UserFeedbackRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserFeedbackService {

    @Autowired
    private UserFeedbackRepository repository;
    private final Logger LOG = LoggerFactory.getLogger( UserFeedbackService.class );

    public boolean createOrUpdate( UserFeedback feedback ) {
        try {
            repository.save( feedback );
            return true;
        }
        catch( IllegalArgumentException ex ) {
            LOG.error( "UserFeedback service failed: {}", ex.getMessage() );
            return false;
        }
    }

    public UserFeedback findUserFeedbackById( UUID id ) {
        var optional = repository.findById( id );

        return optional.isEmpty() ? null : optional.get();
    }

    public void delete( UserFeedback feedback ) throws RuntimeException {

        try { repository.delete( feedback ); }
        catch ( IllegalArgumentException ex ) {
            LOG.error( "DutyService failed: {}", ex.getMessage() );
            throw new RuntimeException( "UserFeedbackService failed:" + ex.getMessage() );
        }
    }
}