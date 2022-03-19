package com.femass.resourceserver.domain.account;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter

@Embeddable
public class AgentCredentials {

    @Column( name = "senha", nullable = false )
    private String password;
    
    @Column( name = "cpf", nullable = false, unique = true, length = 11 )
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

   @Override
    public String toString() { return cpf; }
}