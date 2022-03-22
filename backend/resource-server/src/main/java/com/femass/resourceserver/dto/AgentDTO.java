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

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter @Setter

@JsonNaming( value = PropertyNamingStrategies.SnakeCaseStrategy.class )
@JsonInclude( JsonInclude.Include.NON_EMPTY )

@JsonIgnoreProperties(
    value = { "id", "password", "authorities", "active" },
    allowSetters = true,
    ignoreUnknown = true
)
public class AgentDTO implements Serializable {

    private UUID id;
    private String name;

    @NotNull
    private String username;
    private String cpf;
    private String password;
    private List<SimpleGrantedAuthority> authorities;
    private boolean active;
    private DepartmentDTO department;

    @JsonValue
    public static AgentDTO serialize( Agent agent ) {
        var agentDto = new AgentDTO();
        agentDto.setName( agent.getName() );
        agentDto.setUsername( agent.getAccount().getUsername() );

        var dept = agent.getDepartment();

        if( dept != null )
            agentDto.setDepartment( DepartmentDTO.serialize( dept ) );

        return agentDto;
    }

    public static Agent deserialize(AgentDTO agentDto, ServiceModule module ) {

        var agent = module.getAgentService().findByUsername( agentDto.username );

        if( agent == null ) {

            var account = new AgentAccount();
            account.setUsername( agentDto.username );
            account.setCredentials( new AgentCredentials()  );

            agent = new Agent( agentDto.name, account );
        }

        var encoder = module.getPasswordEncoder();
        var agentAccount = agent.getAccount();

        if( agentDto.name != null )
            agent.setName( agentDto.name );

        if( agentDto.authorities != null && !agentDto.authorities.isEmpty() )
            agentAccount.setAuthorities( agentDto.authorities );

        if( agentDto.cpf != null )
            agentAccount.getCredentials().setCpf( agentDto.cpf );

        if( agentDto.password != null )
            agentAccount.getCredentials()
                    .setPassword( encoder.encode( agentDto.password )  );

        if( agentDto.department != null ) {
            var deptService = module.getDepartmentService();
            var dept = deptService.findDepartmentByName( agentDto.department.getName() );
            agent.setDepartment( dept );
        }

        return agent;
    }
}