package com.femass.resourceserver.services;

import com.femass.resourceserver.domain.Address;
import com.femass.resourceserver.domain.Call;
import com.femass.resourceserver.domain.Status;
import com.femass.resourceserver.repositories.CallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class CallService {

    @Autowired
    private CallRepository repository;

    public void create() {

    }

    public void update() {

    }

    public void delete(){

    }

    public Call findCallByProtocol( String protocol ) {
        var optional = repository.findByProtocol( protocol );

        if( optional.isEmpty() ) return null;
        else return optional.get();
    }

    public List<Call> findCallByAuthor( String authorUsername ) {
        return repository.findByAuthor_Username( authorUsername );
    }

    public List<Call> findCallByPostingDate( String plainTimestamp ) throws IllegalArgumentException {
        var time = Timestamp.valueOf( plainTimestamp );
        return repository.findByPostingDate( time );
    }

    public List<Call> findCallByDuty( String dutyDescription ) {
        return repository.findByDuty_Description( dutyDescription );
    }

    public List<Call> findCallByDepartment( String departmentName ) {
        return repository.findByDepartment_Name( departmentName );
    }

    public List<Call> findCallByAddress( Address address ) {
        return repository.findByAddress( address );
    }

    public List<Call> findCallByZipCode( String addressZipCode ) {
        return repository.findByAddress_ZipCode( addressZipCode );
    }

    public List<Call> findCallByDistrict( String addressDistrict ) {
        return repository.findByAddress_District( addressDistrict );
    }

    public List<Call> findCallByPublicPlace( String addressPublicPlace ) {
        return repository.findByAddress_PublicPlace( addressPublicPlace );
    }

    public List<Call> findCallByStatus( Status status ) {
        return repository.findByStatus( status );
    }
}