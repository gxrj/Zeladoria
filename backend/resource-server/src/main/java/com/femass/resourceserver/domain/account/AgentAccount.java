package com.femass.resourceserver.domain.account;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor
@Entity( name = "ContaColaborador" )
public class AgentAccount implements Account {

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