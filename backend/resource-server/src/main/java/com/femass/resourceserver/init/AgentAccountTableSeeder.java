package com.femass.resourceserver.init;

import com.femass.resourceserver.domain.account.AgentAccount;
import com.femass.resourceserver.domain.account.AgentCredentials;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public class AgentAccountTableSeeder {

    public static void seed( TableSeeder seeder ) throws RuntimeException {

        var accountService = seeder
                                    .getServiceModule()
                                        .getAgentAccountService();

        var encoder = seeder.getEncoder();

        if( accountService.countAgentAccounts() == 0 ) {

            var cred = new AgentCredentials( encoder.encode("123"), "010203" );

            var authorities = List.of(
                                    new SimpleGrantedAuthority( "ROLE_ADMIN" ),
                                    new SimpleGrantedAuthority( "ROLE_AGENT" ) );

            var account = new AgentAccount( "agent", cred, authorities );

            if( !accountService.createOrUpdate( account ) )
                throw new RuntimeException( "AgentAccount seeder failed!" );
        }
    }
}