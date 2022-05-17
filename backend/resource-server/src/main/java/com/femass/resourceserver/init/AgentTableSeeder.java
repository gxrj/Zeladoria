package com.femass.resourceserver.init;

import com.femass.resourceserver.domain.Agent;

import com.femass.resourceserver.domain.account.AgentAccount;
import com.femass.resourceserver.domain.account.AgentCredentials;
import com.femass.resourceserver.services.ServiceModule;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

public class AgentTableSeeder {

    public static void seed( TableSeeder seeder ) throws RuntimeException {

        var encoder = seeder.getEncoder();
        var module = seeder.getServiceModule();
        var agentService = module.getAgentService();

        if( agentService.countAgents() == 0 ) {

            var cpfs = new String[] { "010203", "00000" };
            var names = new String[] { "Pedro", "Jeronimo" };
            var usernames = new String[] { "agent", "admin" };
            var roles = new String[] { "super agent", "admin" };
            var password = encoder.encode( "123" );
            var depts = new String[] { "Secretaria De Infraestrutura", "Inova Macae" };

            var accounts = populateAccounts( usernames, cpfs, password, roles );
            var agents = populateAgents( module, accounts, names, depts );

            if( !agentService.createMultiple( agents ) )
                throw new RuntimeException( "AgentTableSeeder failed!" );
        }
    }

    private static List<Agent> populateAgents(
                    ServiceModule module, List<AgentAccount> accounts, String[] names, String[] depts ) {
        var deptService = module.getDepartmentService();

        ArrayList<Agent> agents = new ArrayList<>();

        for( int i = 0; i < accounts.size(); i++ ) {
            var entity = new Agent( names[i], accounts.get(i) );
            entity.setDepartment( deptService.findDepartmentByName( depts[i] ) );
            agents.add( entity );
        }
        return agents;
    }
    private static List<AgentAccount> populateAccounts(
            String[] usernames, String[] cpfs, String password, String[] roles ) {

        ArrayList<AgentAccount> accounts = new ArrayList<>();

        for( int i = 0; i < usernames.length; i++ ) {
            var cred = new AgentCredentials( password, cpfs[i] );
            accounts.add( new AgentAccount( usernames[i], cred, retrieveCorrectRoles( roles[i] ) ) );
        }

        return accounts;
    }
    private static List<SimpleGrantedAuthority> retrieveCorrectRoles( String role ) {
        var adminRole = new SimpleGrantedAuthority( "ROLE_ADMIN" );
        var agentRole = new SimpleGrantedAuthority( "ROLE_AGENT" );

        List<SimpleGrantedAuthority> authorities;

        switch ( role ) {
            case "super agent" -> authorities = List.of( adminRole, agentRole );
            case "admin" -> authorities = List.of( adminRole );
            default -> authorities = List.of( agentRole );
        }

        return authorities;
    }
}