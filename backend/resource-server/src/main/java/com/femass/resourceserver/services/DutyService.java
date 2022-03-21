package com.femass.resourceserver.services;

import com.femass.resourceserver.domain.Duty;
import com.femass.resourceserver.repositories.DutyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DutyService {

    private final Logger LOG = LoggerFactory.getLogger( DutyService.class );

    @Autowired
    private DutyRepository repository;

    public boolean createOrUpdate( Duty duty ) {

        try{
            repository.save( duty );
            return true;
        }
        catch ( IllegalArgumentException ex ){
            LOG.error( "DutyService failed: {}", ex.getMessage() );
            return false;
        }
    }

    public boolean createMultiple( List<Duty> duties ) {

        try{
            repository.saveAllAndFlush( duties );
            return true;
        }
        catch ( IllegalArgumentException ex ){
            LOG.error( "DutyService failed: {}", ex.getMessage() );
            return false;
        }
    }

    public List<Duty> findAllDuties() { return repository.findAll(); }

    public Duty findDutyByDescription( String description ) {
        var optional = repository.findByDescription( description );

        return optional.isEmpty() ? null : optional.get();
    }

    public List<Duty> findDutyByDepartment( String deptName ) {
        return repository.findByDepartment_Name( deptName );
    }

    public List<Duty> findDutyByCategory( String categoryName ) {
        return repository.findByDutyGroup_Name( categoryName );
    }

    public void delete( Duty duty ) throws RuntimeException {

        try { repository.delete( duty ); }
        catch ( IllegalArgumentException ex ) {
            LOG.error( "DutyService failed: {}", ex.getMessage() );
            throw new RuntimeException( "DutyService failed:" + ex.getMessage() );
        }
    }

    public long countDuties() {
        return repository.count();
    }
}