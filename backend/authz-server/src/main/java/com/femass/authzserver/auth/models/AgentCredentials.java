package com.femass.authzserver.auth.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

@NoArgsConstructor
public class AgentCredentials {

    private String password;
    private String cpf;

    public AgentCredentials( String password, String cpf ) {
        this.password = password;
        this.cpf = cpf;
    }
}