package com.femass.resourceserver.domain.entity;

import javax.persistence.Entity;

import com.femass.resourceserver.domain.trait.Cidadao;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode

@Entity( name = "anonimo" )
public class CidadaoAnonimo implements Cidadao {
    
    @Override
    public String toString(){ return "Usuario anonimo"; }
}
