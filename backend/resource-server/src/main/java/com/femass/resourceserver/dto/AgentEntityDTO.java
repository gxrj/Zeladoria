package com.femass.resourceserver.dto;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.femass.resourceserver.domain.user.AgentCredentials;
import com.femass.resourceserver.domain.user.AgentEntity;
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
public class AgentEntityDTO implements Serializable {

    private UUID id;
    private String name;

    @NotNull
    private String username;
    private String cpf;
    private String password;
    private List<SimpleGrantedAuthority> authorities;
    private boolean active;

    @JsonValue
    public static AgentEntityDTO serialize( AgentEntity agent ) {
        var agentDto = new AgentEntityDTO();
        agentDto.setName( agent.getName() );
        agentDto.setUsername( agent.getUsername() );

        return agentDto;
    }

    public static AgentEntity deserialize( AgentEntityDTO agentDto, ServiceModule module ) {

        var agent = module.getAgentService().findByUsername( agentDto.username );

        if( agent == null ) {

            agent = new AgentEntity();
            agent.setUsername( agentDto.username );

            var credentials = new AgentCredentials();
            agent.setCredentials( credentials );
        }

        var encoder = module.getPasswordEncoder();

        if( agentDto.cpf != null )
            agent.getCredentials().setCpf( agentDto.cpf );
        if( agentDto.password != null )
            agent.getCredentials().setPassword( encoder.encode( agentDto.password )  );
        if( agentDto.name != null )
            agent.setName( agentDto.name );
        if( agentDto.authorities != null && !agentDto.authorities.isEmpty() )
            agent.setAuthorities( agentDto.authorities );

        return agent;
    }
}