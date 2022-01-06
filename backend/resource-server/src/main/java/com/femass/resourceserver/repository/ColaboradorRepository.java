package com.femass.resourceserver.repository;

import java.util.UUID;

import com.femass.resourceserver.domain.entity.Colaborador;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ColaboradorRepository extends JpaRepository <Colaborador, UUID> { }