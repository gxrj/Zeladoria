package com.femass.resourceserver.services;

import com.femass.resourceserver.domain.Address;
import com.femass.resourceserver.domain.Call;
import com.femass.resourceserver.domain.Status;
import com.femass.resourceserver.repositories.CallRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class CallService {

    @Autowired
    private CallRepository repository;

    private final Logger LOG = LoggerFactory.getLogger( CallService.class );

    public boolean createOrUpdate( Call call ) {
        try{
            repository.save( call );
            return true;
        }
        catch( IllegalArgumentException ex ){
            LOG.error( "CallService failed: {}", ex.getMessage() );
            return false;
        }
    }

    public Call persist( Call call ) {

        Call entity;

        try{
            entity = repository.saveAndFlush( call );
        }
        catch( IllegalArgumentException ex ){
            LOG.error( "CallService failed: {}", ex.getMessage() );
            entity = null;
        }

        return entity;
    }

    public void delete( Call entity ) throws RuntimeException {
        try { repository.delete( entity ); }
        catch ( IllegalArgumentException ex ) {
            LOG.error( "CallService failed: {}", ex.getMessage() );
            throw new RuntimeException( "CallService failed:" + ex.getMessage() );
        }
    }

    public long countCalls() { return repository.count(); }

    public Call findCallById( UUID id ) {
        var optional = repository.findById( id );

        return optional.isEmpty() ? null : optional.get();
    }

    public Call findCallByProtocol( String protocol ) {
        var optional = repository.findByProtocol( protocol );
        return optional.isEmpty() ? null : optional.get();
    }

    public List<Call> findAll() {
        return repository.findAll();
    }

    public List<Call> findCallByAuthor( String authorUsername ) {
        return repository.findByAuthor_Account_Username( authorUsername );
    }

    public List<Call> findCallByPostingDate( String plainTimestamp ) throws IllegalArgumentException {
        var time = Timestamp.valueOf( plainTimestamp );
        return repository.findByPostingDate( time );
    }

    public List<Call> findCallByDestination( String deptName, String status ) {

        var statusArray = new ArrayList<Status>();
        status = status == null ? "" : status;

        switch ( status ) {
            case "Avaliada" -> statusArray.addAll( List.of( Status.FINISHED, Status.NOT_SOLVED ) );
            case "Respondida" -> statusArray.add( Status.ANSWERED );
            case "Indeferida" -> statusArray.add( Status.REJECTED );
            default -> statusArray.addAll( List.of( Status.PROCESSING, Status.FORWARDED, Status.NOT_SOLVED ) );
        }

        return repository
                .findByDestination_Name( deptName ).parallelStream()
                    .filter( item -> statusArray
                                        .contains( item.getStatus() ) )
                        .toList();
    }


    public List<Call> findCallByDepartment( String deptName ) {
        return repository.findByDuty_Department_Name( deptName );
    }

    public List<Call> findCallByDuty( String dutyDescription ) {
        return repository.findByDuty_Description( dutyDescription );
    }

    public List<Call> findCallByAddress( Address address ) {
        return repository.findByAddress( address );
    }

    public List<Call> findCallByZipCode( String addressZipCode ) {
        return repository.findByAddress_ZipCode( addressZipCode );
    }

    public List<Call> findCallByDistrict( String addressDistrict ) {
        return repository.findByAddress_District_Name( addressDistrict );
    }

    public List<Call> findCallByPublicPlace( String addressPublicPlace ) {
        return repository.findByAddress_PublicPlace( addressPublicPlace );
    }

    public List<Call> findCallByStatus( Status status ) {
        return repository.findByStatus( status );
    }
}