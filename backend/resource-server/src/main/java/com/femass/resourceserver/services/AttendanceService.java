package com.femass.resourceserver.services;

import com.femass.resourceserver.domain.Attendance;
import com.femass.resourceserver.domain.AttendanceRating;
import com.femass.resourceserver.domain.Department;
import com.femass.resourceserver.repositories.AttendanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
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

    public Attendance findAttendanceByProtocol( String protocol ) {
        var optional = repository.findByProtocol( protocol );
        return optional.isEmpty() ? null : optional.get();
    }

    public List<Attendance> findAttendanceByCallProtocol( String callProtocol ) {
        return repository.findByUserCall_Protocol( callProtocol );
    }

    public List<Attendance> findAttendanceByAgent( String responsible ) {
        return repository.findByResponsible_Account_Username( responsible );
    }

    public List<Attendance> findAttendanceByDepartment( String deptName ) {
        return repository.findByDepartment_Name( deptName );
    }

    public List<Attendance> findAll() {
        return repository.findAll();
    }

    public void delete( Attendance entity ) throws RuntimeException {
        try { repository.delete( entity ); }
        catch( IllegalArgumentException ex ) {
            LOG.error( "AttendanceService failed: {}", ex.getMessage() );
            throw new RuntimeException( "AttendanceService failed: " + ex.getMessage() );
        }
    }

    public List<Attendance> findAttendanceByRating( String rating, Department dept ) {

        List<Attendance> list;

        switch( rating ) {
            case "positiva" -> list = repository.findByRating(AttendanceRating.POSITIVE);
            case "negativa" -> list = repository.findByRating(AttendanceRating.NEGATIVE);
            default -> list = repository.findByRating(AttendanceRating.NOT_RATED);
        }

        if( dept == null )
            return list;
        else
            return list.parallelStream()
                        .filter( el -> el.getDepartment().equals( dept ) )
                            .toList();
    }

    public List<Attendance> findAttendanceByInterval( Timestamp start, Timestamp end, String deptName ) {
        var property = new String[]{ "department", "executionDate" };
        var result = repository.findByExecutionDateBetween( start, end, Sort.by( property ) );

        if( deptName == null || deptName.equals( "" ) ) return result;

        return result.parallelStream()
                .filter(
                    el -> el.getDepartment().getName()
                                .equalsIgnoreCase( deptName ) )
                .toList();
    }
}