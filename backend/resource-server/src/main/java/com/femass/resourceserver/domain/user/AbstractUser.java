package com.femass.resourceserver.domain.user;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode

@MappedSuperclass
public class AbstractUser {
    
    @Id
    @Column( name = "id" )
    @GeneratedValue( strategy = GenerationType.AUTO )
    protected UUID id;

    @Column( name = "nome", nullable = false, length = 50 )
    protected String username;

    @Column( name = "habilitada", nullable = false, length = 120 )
    protected Boolean enabled;

    @ElementCollection( fetch = FetchType.EAGER )
    @Column( name = "authorizacoes" )
    protected List< SimpleGrantedAuthority > autorities;

    protected AbstractUser(){ this.enabled = true; }

    protected AbstractUser( String username ){
        this();
        this.username = username;
    }

    protected AbstractUser( String username, 
                            List< SimpleGrantedAuthority > authorities ) {
    
            this();
            this.username = username;
            this.autorities = authorities;
    }
}