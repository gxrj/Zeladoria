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
    @Column( name = "id", columnDefinition = "uuid not null" )
    @GeneratedValue( strategy = GenerationType.AUTO )
    protected UUID id;

    @Column( name = "nome", nullable = false, length = 50 )
    protected String name;

    protected String username; /* Overriden by children classes */

    @Column( name = "habilitada", nullable = false, length = 120 )
    protected Boolean enabled;

    @ElementCollection( fetch = FetchType.EAGER )
    @Fetch( value = FetchMode.SUBSELECT )
    @Column( name = "autorizacoes" )
    protected List< SimpleGrantedAuthority > authorities;

    protected AbstractUser(){ this.enabled = true; }

    protected AbstractUser( String username ) {
        this();
        this.username = username;
    }

    protected AbstractUser( String username, 
                            List< SimpleGrantedAuthority > authorities ) {
    
            this();
            this.username = username;
            this.authorities = authorities;
    }

    public List< SimpleGrantedAuthority > getAuthorities() { return this.authorities; }
}