package com.femass.resourceserver.init;

import com.femass.resourceserver.domain.user.UserEntity;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public class UserEntityTableSeeder {

    public static void seed( TableSeeder seeder ) throws RuntimeException {

        var userService = seeder.getServiceModule().getUserService();
        var encoder = seeder.getEncoder();

        if( userService.countUsers() == 0 ) {
            var user = new UserEntity();
            user.setName( "usuario" );
            user.setUsername( "user" );
            user.setPassword( encoder.encode( "123" ) );
            user.setAuthorities( List.of( new SimpleGrantedAuthority( "ROLE_USER" ) ) );

            if ( !userService.createOrUpdate( user ) )
                throw new RuntimeException( "UserEntity seeder failed" );
        }
    }
}