package com.femass.authzserver.auth.models.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@ToString

@Entity( name = "ContaCidadao" )
public class CitizenAccount extends Account {

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
}
