package com.femass.resourceserver.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Embeddable
public class AgentCredentials {

    @Column( name = "senha", nullable = false )
    private String password;
    
    @Column( name = "cpf", unique = true, length = 11 )
    private String cpf;

    public AgentCredentials() {}

    public AgentCredentials( String password, String cpf ) {
        this.password = password;
        this.cpf = cpf;
    }

     /* 
        Lombok's getter and setter arent taking 
        effect on VSCode even with is lombok extention
    */
    public String getPassword() { return this.password; }

    public String getCpf() { return this.cpf; }


    public boolean isNull(){
        return Objects.isNull( this );
    }
}