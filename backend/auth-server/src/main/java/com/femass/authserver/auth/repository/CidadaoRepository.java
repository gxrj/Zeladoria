package com.femass.authserver.auth.repository;

import java.util.UUID;

import com.femass.authserver.auth.domain.entities.Cidadao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CidadaoRepository extends JpaRepository<Cidadao, UUID> { }