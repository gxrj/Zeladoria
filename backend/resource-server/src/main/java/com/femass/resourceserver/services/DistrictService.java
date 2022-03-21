package com.femass.resourceserver.services;

import com.femass.resourceserver.domain.District;
import com.femass.resourceserver.repositories.DistrictRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistrictService {

    @Autowired
    private DistrictRepository repository;

    private final Logger LOG = LoggerFactory.getLogger( DistrictService.class );

    public boolean createOrUpdate( District district ) {

        try{
            repository.save( district );
            return true;
        }
        catch( IllegalArgumentException ex ){
            LOG.error( "DistrictService failed: {}", ex.getMessage() );
            return false;
        }
    }

    public boolean createMultiple( List<District> districtList ) {
        try{
            repository.saveAllAndFlush( districtList );
            return true;
        }
        catch( IllegalArgumentException ex ){
            LOG.error( "DistrictService failed: {}", ex.getMessage() );
            return false;
        }
    }

    public List<District> loadAll() {
        return repository.findAll();
    }

    public District findDistrictByName( String districtName ) {
        var district = repository.findByName( districtName );
        return district.orElse(null);
    }

    public void delete( District district ) throws RuntimeException {

        try{ repository.delete( district ); }
        catch( IllegalArgumentException ex ){
            LOG.error( "DistrictService failed: {}", ex.getMessage() );
            throw new RuntimeException( "DistrictService failed: "+ ex.getMessage() );
        }
    }

    public int countDistricts() { return (int) repository.count(); }
}