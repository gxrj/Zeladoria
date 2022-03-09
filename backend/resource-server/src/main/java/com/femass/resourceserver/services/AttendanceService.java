package com.femass.resourceserver.services;

import com.femass.resourceserver.domain.Attendance;
import com.femass.resourceserver.repositories.AttendanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository repository;
    private final Logger LOG = LoggerFactory.getLogger( AttendanceService.class );

    public boolean createOrUpdate( Attendance attendance ) {

        try{
            repository.save( attendance );
            return true;
        }
        catch( IllegalArgumentException ex ){
            LOG.error( "AttendanceService failed: {}", ex.getMessage() );
            return false;
        }
    }

    public Attendance findAttendanceById( UUID id ) {
         var optional = repository.findById( id );

        return optional.isEmpty() ? null : optional.get();
    }

    public void delete() {

    }
}