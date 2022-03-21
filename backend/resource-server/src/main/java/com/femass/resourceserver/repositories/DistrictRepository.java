package com.femass.resourceserver.repositories;

import com.femass.resourceserver.domain.District;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {
    Optional<District> findByName( String districtName );
}