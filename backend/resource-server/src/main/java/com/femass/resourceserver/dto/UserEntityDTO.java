package com.femass.resourceserver.dto;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.femass.resourceserver.domain.user.UserEntity;
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
public class UserEntityDTO implements Serializable {

    private UUID id;
    private String name;
    @NotNull
    private String email;
    private String password;
    private boolean active;

    @JsonValue
    public static UserEntityDTO serialize( UserEntity user ) {

        var userDto = new UserEntityDTO();
        userDto.setName( user.getName() );
        userDto.setEmail( user.getUsername() );

        return userDto;
    }

    public static UserEntity deserialize( UserEntityDTO userDto, ServiceModule module ) {

        var user = module.getUserService().findByUsername( userDto.email );

        if( user == null ) {

            var userRole = new SimpleGrantedAuthority( "USER_ROLE" );

            user = new UserEntity();
            user.setUsername( userDto.email );
            user.setAuthorities( List.of( userRole ) );
        }

        var encoder = module.getPasswordEncoder();

        if( userDto.name != null )
            user.setName( userDto.name );
        if( userDto.password != null )
            user.setPassword( encoder.encode( userDto.password ) );

        return user;
    }
}