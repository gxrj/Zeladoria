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

@Entity( name = "Cidadao" )
public class Cidadao extends Conta {
    
    @Column( name = "email", unique = true, nullable = false )
    private String email;

    public Cidadao( String nome, String email, String senha, Boolean ativacao ){
        super( nome, senha, ativacao );
        this.email = email;
    }

    @Override
    public String getLogin(){ return this.email; }

    @Override
    public String getCpf() throws NoSuchFieldException { throw new NoSuchFieldException(); }

}