package com.femass.resourceserver.domain.entity;

import com.femass.resourceserver.domain.abstracts.Conta;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

@NoArgsConstructor

@Entity( name = "Cidadao" )
public class Cidadao extends Conta implements Serializable {
    
    @Column( name = "email", unique = true, nullable = false, length = 50 )
    private String email;

    public Cidadao( String nome, String email, String senha, Boolean ativacao ){
        super( nome, senha, ativacao );
        this.email = email;
    }

    @Override
    public String getUsername(){ return this.getEmail(); }
}