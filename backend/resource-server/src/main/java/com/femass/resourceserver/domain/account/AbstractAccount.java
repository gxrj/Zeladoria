package com.femass.resourceserver.domain.account;

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
public abstract class AbstractAccount implements Serializable {

    @Id
    @Column( name = "id", columnDefinition = "uuid not null" )
    @GeneratedValue( strategy = GenerationType.AUTO )
    protected UUID id;

    @ElementCollection( fetch = FetchType.EAGER )
    @Fetch( value = FetchMode.SUBSELECT )
    @Column( name = "autoridades" )
    protected List<SimpleGrantedAuthority> authorities;

    protected String username; /* Overriden by children classes */

    @Column( name = "habilitada", nullable = false )
    protected Boolean enabled;

    protected AbstractAccount() {
        this.enabled = true;
    }

    protected AbstractAccount( String username,
                               List<SimpleGrantedAuthority> authorities ) {
        this();
        this.username = username;
        this.authorities = authorities;
    }
}