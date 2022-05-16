package com.femass.resourceserver.dto;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.femass.resourceserver.domain.account.AgentAccount;
import com.femass.resourceserver.domain.account.AgentCredentials;
import com.femass.resourceserver.domain.Agent;
import com.femass.resourceserver.services.ServiceModule;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.validation.constraints.NotEmpty;
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
public class AgentDTO implements Serializable {

    private UUID id;
    private String name;
    @NotEmpty
    private String username;
    private String cpf;
    private String password;
    private String isAdmin;
    private String active;
    private DepartmentDTO department;

    @JsonValue
    public static AgentDTO serialize( Agent agent ) {

        if( agent == null ) return null;

        var agentDto = new AgentDTO();
        agentDto.setName( agent.getName() );
        agentDto.setUsername( agent.getAccount().getUsername() );
        agentDto.setIsAdmin( checkAdminAuthority( agent ) );

        var dept = agent.getDepartment();

        if( dept != null )
            agentDto.setDepartment( DepartmentDTO.serialize( dept ) );

        return agentDto;
    }

    public static Agent deserialize( AgentDTO agentDto, ServiceModule module ) {

        var agent = module.getAgentService().findByUsername( agentDto.username );

        if( agent == null ) {

            var account = new AgentAccount();
            account.setUsername( agentDto.username );
            account.setCredentials( new AgentCredentials()  );
            account.setAuthorities( List.of( new SimpleGrantedAuthority( "ROLE_AGENT" ) ) );

            agent = new Agent( agentDto.name, account );
        }

        var encoder = module.getPasswordEncoder();
        var agentAccount = setAuthorities( agent.getAccount(), agentDto );

        if( agentDto.cpf != null )
            agentAccount.getCredentials().setCpf( agentDto.cpf );

        if( agentDto.password != null )
            agentAccount.getCredentials()
                    .setPassword( encoder.encode( agentDto.password ) );

        if( agentDto.active != null )
            agentAccount.setEnabled( !agentDto.active.equalsIgnoreCase( "false" ) );

        if( agentDto.name != null )
            agent.setName( agentDto.name );

        if( agentDto.department != null ) {
            var deptService = module.getDepartmentService();
            var dept = deptService.findDepartmentByName( agentDto.department.getName() );
            agent.setDepartment( dept );
        }

        agent.setAccount( agentAccount );

        return agent;
    }

    private static AgentAccount setAuthorities ( AgentAccount agentAccount, AgentDTO agentDto ) {
        var adminRole = new SimpleGrantedAuthority( "ROLE_ADMIN" );
        var containsAdminRole = agentAccount.getAuthorities().contains( adminRole );

        if( agentDto.isAdmin != null ) {
            if ( agentDto.isAdmin.equalsIgnoreCase("true" ) && !containsAdminRole )
                agentAccount.getAuthorities().add( adminRole );

            if ( containsAdminRole && agentDto.isAdmin.equalsIgnoreCase( "false" ) )
                agentAccount.getAuthorities().remove( adminRole );
        }
        return agentAccount;
    }

    private static String checkAdminAuthority( Agent agent ) {
        var agentAuthorities = agent.getAccount().getAuthorities();
        var result = agentAuthorities.parallelStream()
                                                    .filter( el -> el.getAuthority().equals( "ROLE_ADMIN" ) )
                                                        .toList();
        if( result.size() > 0 )
            return "true";
        else
            return "false";
    }
}