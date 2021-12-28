package com.femass.resourceserver.domain.entity;

import javax.persistence.Entity;

import com.femass.resourceserver.domain.model.Conta;
import com.femass.resourceserver.domain.trait.Cidadao;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode

@Entity( name = "cidadao" )
public class CidadaoRegistrado extends Conta implements Cidadao {
    
}
