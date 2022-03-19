package com.femass.resourceserver.domain.account;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@Entity( name = "ContaColaborador" )
public class AgentAccount extends Account {

    @Column( name = "matricula", nullable = false, unique = true, length = 10 )
    private String username;

    @Embedded
    private AgentCredentials credentials;

    public AgentAccount( String username,
                         AgentCredentials credentials,
                         List<SimpleGrantedAuthority> authorities ) {

        this.username = username;
        this.credentials = credentials;
        this.authorities = authorities;
    }
}