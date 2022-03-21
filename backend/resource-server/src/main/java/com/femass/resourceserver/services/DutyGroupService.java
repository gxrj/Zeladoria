package com.femass.resourceserver.services;

import com.femass.resourceserver.domain.DutyGroup;
import com.femass.resourceserver.repositories.DutyGroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DutyGroupService {

    private final Logger LOG = LoggerFactory.getLogger( DutyGroupService.class );

    @Autowired
    private DutyGroupRepository repository;

    public boolean createOrUpdate( DutyGroup dutyGroup ) {

        try{
            repository.save( dutyGroup );
            return true;
        }
        catch ( IllegalArgumentException ex ){
            LOG.error( "DutyGroupService failed: {}", ex.getMessage() );
            return false;
        }
    }

    public boolean createMultiple( List<DutyGroup> categories ) {

        try{
            repository.saveAllAndFlush( categories );
            return true;
        }
        catch ( IllegalArgumentException ex ) {
            LOG.error( "DutyGroupService failed: {}", ex.getMessage() );
            return false;
        }
    }

    public List<DutyGroup> findAllDutyGroup() {
        return repository.findAll();
    }

    public DutyGroup findDutyGroupByName( String groupName ) {
        var optional = repository.findByName( groupName );

        return optional.isEmpty() ? null : optional.get();
    }

    public void delete( DutyGroup category ) throws RuntimeException {

        try { repository.delete( category ); }
        catch ( IllegalArgumentException ex ) {
            LOG.error( "DutyGroupService failed: {}", ex.getMessage() );
            throw new RuntimeException( "DutyService failed:" + ex.getMessage() );
        }
    }

    public long countDutyGroups() {
        return repository.count();
    }
}
