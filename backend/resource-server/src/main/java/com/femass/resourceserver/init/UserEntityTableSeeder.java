package com.femass.resourceserver.init;

import com.femass.resourceserver.domain.user.UserEntity;
import com.femass.resourceserver.services.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public class UserEntityTableSeeder {

    public static void seed( UserService userService, PasswordEncoder encoder ) {

        if( userService.countUsers() == 0 ) {
            var user = new UserEntity();
            user.setName( "usuario" );
            user.setUsername( "user" );
            user.setPassword( encoder.encode( "123" ) );
            user.setAuthorities( List.of( new SimpleGrantedAuthority( "ROLE_USER" ) ) );

            if ( !userService.createOrUpdate( user ) ) throw new RuntimeException( "UserEntity seeder failed" );
        }
    }
}