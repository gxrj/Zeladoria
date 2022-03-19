package com.femass.authzserver.auth.models.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter @Setter

@MappedSuperclass
public abstract class Account implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column( columnDefinition = "uuid not null" )
    private UUID id;

    @Column( name = "habilitada", nullable = false )
    protected Boolean enabled = true;

    @ElementCollection( fetch = FetchType.EAGER )
    @Fetch( value = FetchMode.SELECT )
    @Column( name = "autoridades" )
    protected List<SimpleGrantedAuthority> authorities;

    abstract String getUsername();
    abstract <T> T getCredentials();
}
