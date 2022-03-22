package com.femass.resourceserver.init;

import com.femass.resourceserver.domain.Agent;

import org.springframework.util.Assert;

public class AgentTableSeeder {

    public static void seed( TableSeeder seeder ) throws RuntimeException {

        var module = seeder.getServiceModule();
        var agentService = module.getAgentService();
        var accountService = module.getAgentAccountService();
        var deptService = module.getDepartmentService();

        if( agentService.countAgents() == 0 ) {

            var account = accountService.findByUsername( "agent" );
            Assert.notNull( account, "Agent account not found!" );

            var agent = new Agent();
            agent.setName( "servidor publico" );
            agent.setAccount( account );
            agent.setDepartment(
                    deptService.findDepartmentByName( "Secretaria De Infraestrutura" ) );

            if( !agentService.createOrUpdate( agent ) )
                throw new RuntimeException( "AgentTableSeeder failed!" );
        }
    }
}