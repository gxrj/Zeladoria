package com.femass.resourceserver.repositories;

import com.femass.resourceserver.domain.Address;
import com.femass.resourceserver.domain.Call;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CallRepository extends JpaRepository< Call, UUID > {

    Optional<Call> findByProtocol( String protocol );
    List<Call> findByAuthor_Username( String authorUsername );
    List<Call> findByPostingDate( Timestamp postingDate );
    List<Call> findByDuty_Description( String dutyDescription );
    List<Call> findByDepartment_Name( String departmentName );
    List<Call> findByAddress( Address address );
    List<Call> findByAddress_ZipCode( String addressZipCode );
    List<Call> findByAddress_District( String addressDistrict );
    List<Call> findByAddress_PublicPlace( String addressPublicPlace );
    List<Call> findByStatus( String status );
}