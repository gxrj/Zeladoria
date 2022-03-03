package com.femass.authzserver.auth.models.domain;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@MappedSuperclass
public class AbstractUser {
    
    @Id
    @Column( name = "id" )
    @GeneratedValue( strategy = GenerationType.AUTO )
    protected UUID id;

    @Column( name = "nome", nullable = false, length = 50 )
    private String name;
 
    protected String username; /* Overriden by children classes */

    @Column( name = "habilitada", nullable = false )
    protected Boolean enabled;

    @ElementCollection( fetch = FetchType.EAGER )
    
    @Column( name = "autorizacoes" )
    @Fetch( FetchMode.SUBSELECT )
    protected List< SimpleGrantedAuthority > authorities;

    protected AbstractUser(){ this.enabled = true; }

    protected AbstractUser( String username ){
        this();
        this.username = username;
    }

    protected AbstractUser( String username, 
                            List< SimpleGrantedAuthority > authorities ) {
    
            this();
            this.username = username;
            this.authorities = authorities;
    }
    
    public List< SimpleGrantedAuthority > getAuthorities () { return this.authorities; }
}
