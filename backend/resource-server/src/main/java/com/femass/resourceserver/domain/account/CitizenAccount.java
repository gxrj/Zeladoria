package com.femass.resourceserver.domain.account;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor
@ToString

@Entity( name = "ContaCidadao" )
public class CitizenAccount implements Account {

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

    @Column( name = "email", nullable = false, unique = true )
    private String username;

    @Column( name = "senha", nullable = false, length = 120 )
    private String credentials;

    public CitizenAccount(
            String username, String credentials,
            List<SimpleGrantedAuthority> authorities ) {
        this.username = username;
        this.credentials = credentials;
        this.authorities = authorities;
    }

    @Override
    public boolean getEnabled() {
        return enabled;
    }
}