package com.femass.resourceserver.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.femass.resourceserver.domain.abstracts.Conta;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

@EqualsAndHashCode
@NoArgsConstructor

@Entity( name = "Colaborador" )
public class Colaborador extends Conta {

    @Column( name = "cpf", unique = true )
    private String cpf;

    @Column( name = "matricula", unique = true )
    private String matricula;

    public Colaborador( String cpf, String matricula, String nome, String senha, Boolean ativacao ){
        super( nome, senha, ativacao );
        this.cpf = cpf;
        this.matricula = matricula;
    }

    @Override
    public String getCpf(){ return this.cpf; }

    @Override
    public String getLogin(){ return this.matricula; }
}