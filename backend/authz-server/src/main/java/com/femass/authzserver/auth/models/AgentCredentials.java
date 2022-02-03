package com.femass.authzserver.auth.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

@NoArgsConstructor

@Embeddable
public class AgentCredentials {

    @Column( name = "senha", nullable = false, length = 60 )
    private String password;
    
    @Column( name = "cpf", unique = true, length = 11 )
    private String cpf;

    public AgentCredentials( String password, String cpf ) {
        this.password = password;
        this.cpf = cpf;
    }
}