package com.femass.authzserver.auth.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;

import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

@NoArgsConstructor

@Entity( name = "Colaborador" )

@AttributeOverride( name = "username", column = @Column( name = "matricula" ) )
public class AgentEntity extends AbstractUser 
        implements Serializable {
    
    @Embedded
    private AgentCredentials credentials;

    public AgentEntity( String username, 
                        AgentCredentials credentials ) {

        super( username );
        this.credentials = credentials;
    }

    public AgentEntity( String username, 
                        AgentCredentials credentials,
                        List< GrantedAuthority > authorities ) {

        super( username, authorities );
        this.credentials = credentials;
    }
}