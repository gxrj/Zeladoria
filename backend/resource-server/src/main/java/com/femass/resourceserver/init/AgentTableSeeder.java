package com.femass.resourceserver.init;

import com.femass.resourceserver.domain.Agent;

import org.springframework.util.Assert;

public class AgentTableSeeder {

    public static void seed( TableSeeder seeder ) throws RuntimeException {

        var agentService = seeder.getServiceModule().getAgentService();
        var accountService = seeder.getServiceModule().getAgentAccountService();

        if( agentService.countAgents() == 0 ) {

            var account = accountService.findByUsername( "agent" );
            Assert.notNull( account, "Agent account not found!" );

            var agent = new Agent();
            agent.setName( "servidor publico" );
            agent.setAccount( account );

            if( !agentService.createOrUpdate( agent ) )
                throw new RuntimeException( "AgentTableSeeder failed!" );
        }
    }
}