package com.femass.resourceserver.services;

import com.femass.resourceserver.domain.Department;
import com.femass.resourceserver.repositories.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final Logger LOG = LoggerFactory.getLogger( DepartmentService.class );

    @Autowired
    private DepartmentRepository repository;

    public boolean createOrUpdate( Department dept ) {
        try{
            repository.save( dept );
            return true;
        }
        catch( IllegalArgumentException ex ) {
            LOG.error( "DepartmentService failed: {}", ex.getMessage() );
            return false;
        }
    }

    public boolean createMultiple( List<Department> list ) {
        try{
            repository.saveAllAndFlush( list );
            return true;
        }
        catch( IllegalArgumentException ex ) {
            LOG.error( "DepartmentService failed: {}", ex.getMessage() );
            return false;
        }
    }

    public void delete( Department dept ) throws RuntimeException {

        try { repository.delete( dept ); }
        catch ( IllegalArgumentException ex ) {
            LOG.error( "DutyService failed: {}", ex.getMessage() );
            throw new RuntimeException( "DutyService failed:" + ex.getMessage() );
        }
    }

    public Department findDepartmentByName( String name ) {
        var optional = repository.findByName( name );

        return optional.isEmpty() ? null : optional.get();
    }

    public Department findDepartmentById( Long id ) {
        var optional = repository.findById( id );

        return optional.isEmpty() ? null : optional.get();
    }

    public long countDepartments() {
        return repository.count();
    }
}