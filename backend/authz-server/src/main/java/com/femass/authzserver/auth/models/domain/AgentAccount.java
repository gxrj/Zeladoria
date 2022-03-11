package com.femass.authzserver.auth.models.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@Entity( name = "ContaColaborador" )
@AttributeOverride(
        name = "username",
        column = @Column( name = "matricula", nullable = false, unique = true, length = 10 )
)
public class AgentAccount extends AbstractAccount {

    @Embedded
    private AgentCredentials credentials;

    public AgentAccount( String username,
                         AgentCredentials credentials,
                         List<SimpleGrantedAuthority> authorities ) {

        super( username, authorities );
        this.credentials = credentials;
    }
}
