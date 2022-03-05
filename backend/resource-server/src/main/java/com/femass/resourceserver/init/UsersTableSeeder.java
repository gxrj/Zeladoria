package com.femass.resourceserver.init;

import com.femass.resourceserver.domain.user.AgentCredentials;
import com.femass.resourceserver.domain.user.AgentEntity;
import com.femass.resourceserver.domain.user.UserEntity;
import com.femass.resourceserver.services.AgentService;
import com.femass.resourceserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsersTableSeeder implements CommandLineRunner {

    @Autowired
    AgentService agentService;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public void run( String... args ) {

        if( userService.existsUserByUsername( "user" ) ) return;

        var userAuthority = new SimpleGrantedAuthority( "ROLE_USER" );
        var user = new UserEntity( "user", encoder.encode( "123" ), List.of( userAuthority ) );
        user.setName( "usuario" );

        userService.create( user );

        if( agentService.existsAgentByUsername( "agent" ) ) return;

        var agentAuthority = new SimpleGrantedAuthority( "ROLE_AGENT" );
        var adminAuthority = new SimpleGrantedAuthority( "ADMIN_AGENT" );
        var agentCredentials = new AgentCredentials( encoder.encode( "123" ), "010203" );

        var agent = new AgentEntity( "agent", agentCredentials, List.of( agentAuthority, adminAuthority ) );
        agent.setName( "servidor publico" );

        agentService.create( agent );
    }
}