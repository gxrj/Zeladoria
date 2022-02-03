package com.femass.authzserver.auth.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;

import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

@NoArgsConstructor

@Entity( name = "Cidadao" )

@AttributeOverride( name ="username", column =  @Column( name = "email" ) )
public class UserEntity extends AbstractUser 
        implements Serializable {

    @Column( name = "senha", nullable = false, length = 60 )
    private String password;

    public UserEntity( String username, String password ){
        super( username );
        this.password = password;
    }
    
    public UserEntity( String username, String password, List< GrantedAuthority > authorities ){
        super( username, authorities );
        this.password = password;
    }
}