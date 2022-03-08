package com.femass.resourceserver.domain.user;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonValue;
import com.nimbusds.jose.shaded.json.JSONObject;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

@NoArgsConstructor

@Entity( name = "Cidadao" )

@AttributeOverride( 
    name = "username", 
    column = @Column( name = "email", nullable = false, unique = true )
)
public class UserEntity extends AbstractUser {

    @Column( name = "senha", nullable = false, length = 120 )
    private String password;

    public UserEntity( String username, String password ){
        super( username );
        this.password = password;
    }
    
    public UserEntity( String username, String password, List< SimpleGrantedAuthority > authorities ) {
        super( username, authorities );
        this.password = password;
    }

    public String getPassword() { return this.password; }

    @JsonValue
    public JSONObject toJson() {

        var json = new JSONObject();
        json.appendField( "name", name );
        json.appendField( "email", username );
        return json;
    }
}