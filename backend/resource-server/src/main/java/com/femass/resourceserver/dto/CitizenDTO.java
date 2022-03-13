package com.femass.resourceserver.dto;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import com.femass.resourceserver.domain.Citizen;
import com.femass.resourceserver.domain.account.CitizenAccount;
import com.femass.resourceserver.services.ServiceModule;

import lombok.Getter;
import lombok.Setter;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter @Setter

@JsonNaming( value = PropertyNamingStrategies.SnakeCaseStrategy.class )
@JsonInclude( JsonInclude.Include.NON_EMPTY )

@JsonIgnoreProperties(
    value = { "id", "password", "active" },
    allowSetters = true,
    ignoreUnknown = true
)
public class CitizenDTO implements Serializable {

    private UUID id;
    private String name;
    @NotNull
    private String email;
    private String password;
    private boolean active;

    @JsonValue
    public static CitizenDTO serialize( Citizen user ) {

        var userDto = new CitizenDTO();
        userDto.setName( user.getName() );
        userDto.setEmail( user.getAccount().getUsername() );

        return userDto;
    }

    public static Citizen deserialize( CitizenDTO userDto, ServiceModule module ) {

        var user = module.getCitizenService().findByUsername( userDto.email );

        if( user == null ) {

            var userRole = new SimpleGrantedAuthority( "USER_ROLE" );
            var authorities = List.of( userRole );

            var account = new CitizenAccount();
            account.setUsername( userDto.email );
            account.setAuthorities( authorities );

            user = new Citizen();
            user.setAccount( account );
        }

        var encoder = module.getPasswordEncoder();

        if( userDto.name != null )
            user.setName( userDto.name );
        if( userDto.password != null )
            user.getAccount().setPassword( encoder.encode( userDto.password ) );

        return user;
    }
}
