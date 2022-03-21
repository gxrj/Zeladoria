package com.femass.resourceserver.repositories;

import com.femass.resourceserver.domain.ForwardStatus;
import com.femass.resourceserver.domain.ForwardedCall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ForwardedCallRepository extends JpaRepository<ForwardedCall, UUID> {
    List<ForwardedCall> findAllByDestination_NameAndStatus( String departmentName,
                                                            ForwardStatus status );
    List<ForwardedCall> findAllByDestination_Name( String departmentName );
    List<ForwardedCall> findAllByStatus( ForwardStatus status );
}