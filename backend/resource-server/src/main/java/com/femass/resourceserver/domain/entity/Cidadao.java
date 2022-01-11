package com.femass.resourceserver.domain.entity;

import com.femass.resourceserver.domain.abstracts.Conta;
import com.femass.resourceserver.domain.model.CidadaoCredentials;

import java.io.Serializable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

@NoArgsConstructor

@Entity( name = "Cidadao" )
public class Cidadao extends Conta implements Serializable {
    
    @Embedded
    private CidadaoCredentials login;

    public Cidadao( String nome, String email, String senha, Boolean ativacao ){
        super( nome, senha, ativacao );
        this.login = CidadaoCredentials.builder()
                                            .email( email )
                                            .build();
    }

    @Override
    public CidadaoCredentials getLogin(){ return this.login; }
}