package com.femass.resourceserver.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.femass.resourceserver.domain.model.Conta;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor

@Entity( name = "Colaborador" )
public class Colaborador extends Conta {

    @Column( name = "cpf", unique = true )
    private String cpf;

    @Column( name = "matricula", unique = true )
    private String matricula;
}
