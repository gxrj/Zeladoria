package com.femass.authserver.auth.repository;

import java.util.UUID;

import com.femass.authserver.auth.domain.entities.Colaborador;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ColaboradorRepository extends JpaRepository<Colaborador, UUID> { }