package com.femass.resourceserver.domain.account;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@ToString

@Entity( name = "ContaCidadao" )
@AttributeOverride(
    name = "username",
    column = @Column( name = "email", nullable = false, unique = true )
)
public class CitizenAccount extends AbstractAccount {

    @Column( name = "senha", nullable = false, length = 120 )
    private String password;

    public CitizenAccount(String username, String password, List<SimpleGrantedAuthority> authorities ) {
        super( username, authorities );
        this.password = password;
    }
}