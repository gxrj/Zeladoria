package com.femass.resourceserver.init;

import com.femass.resourceserver.domain.user.AgentCredentials;
import com.femass.resourceserver.domain.user.AgentEntity;
import com.femass.resourceserver.services.AgentService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public class AgentEntityTableSeeder {

    public static void seed( AgentService agentService, PasswordEncoder encoder ) {

        if( agentService.countAgents() == 0 ) {

            var cred = new AgentCredentials( encoder.encode( "123" ), "010203" );
            var agent = new AgentEntity();

            agent.setName( "servidor publico" );
            agent.setUsername( "agent" );
            agent.setCredentials( cred );

            var authorities = List.of(
                                new SimpleGrantedAuthority( "ROLE_ADMIN" ),
                                new SimpleGrantedAuthority( "ROLE_AGENT" ) );

            agent.setAuthorities( authorities );

            if( !agentService.createOrUpdate( agent ) ) throw new RuntimeException( "AgentService failed" );
        }
    }
}