package com.femass.authserver.auth.domain.users;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

@Entity( name = "Colaborador" )
public class Colaborador extends Conta implements Serializable {
    
    @Column( name = "cpf", unique = true, length = 11 )
    private String cpf;

    @Column( name = "matricula", unique = true, length = 20 )
    private String matricula;

    public Colaborador( String cpf, String matricula, String nome, String senha, Boolean ativacao ){
        super( nome, senha, ativacao );
        this.cpf = cpf;
        this.matricula = matricula;
    }

    public String getMatricula(){ return this.matricula; }

    @Override
    public String getUsername(){ return getMatricula(); }
}