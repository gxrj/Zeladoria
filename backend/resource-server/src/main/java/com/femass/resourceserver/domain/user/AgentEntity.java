package com.femass.resourceserver.domain.user;

import java.io.Serializable;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

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
                        List< SimpleGrantedAuthority > authorities ) {

        super( username, authorities );
        this.credentials = credentials;
    }

    /* 
        Lombok's getter and setter arent taking 
        effect on VSCode even with is lombok extention
    */

    public AgentCredentials getCredentials(){
        return this.credentials;
    }

    public List< SimpleGrantedAuthority > getAuthorities(){
        return this.autorities;
    }
}