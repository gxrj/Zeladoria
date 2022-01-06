package com.femass.resourceserver.repository;

import java.util.UUID;

import com.femass.resourceserver.domain.entity.Cidadao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CidadaoRepository extends JpaRepository <Cidadao, UUID> { }