package com.femass.resourceserver.services;

import com.femass.resourceserver.domain.Department;
import com.femass.resourceserver.repositories.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void delete() {

    }

    public Department findDepartmentByName( String name ) {
        var optional = repository.findByName( name );

        if( optional.isEmpty() ) return null;

        return optional.get();
    }

    public long countDepartments() {
        return repository.count();
    }
}