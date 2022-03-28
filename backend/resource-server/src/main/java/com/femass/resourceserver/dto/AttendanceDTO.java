package com.femass.resourceserver.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import com.femass.resourceserver.domain.Attendance;
import com.femass.resourceserver.services.ServiceModule;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

@Getter @Setter

@JsonNaming( value = PropertyNamingStrategies.SnakeCaseStrategy.class )
@JsonInclude( JsonInclude.Include.NON_EMPTY )

@JsonIgnoreProperties(
    value = { "id" },
    allowSetters = true,
    ignoreUnknown = true
)
public class AttendanceDTO implements Serializable {

    private UUID id;
    private @NotEmpty String protocol;
    private @NotEmpty String callProtocol;
    private Timestamp issuedAt;
    private String description;
    private @NotEmpty AgentDTO responsible;

    @JsonValue
    public static AttendanceDTO serialize( Attendance attendance ) {

        if( attendance == null ) return null;
        
        var attendanceDto = new AttendanceDTO();
        attendanceDto.setId( attendance.getId() );
        attendanceDto.setProtocol( attendanceDto.getProtocol() );
        attendanceDto.setCallProtocol( attendance.getUserCall().getProtocol() );
        attendanceDto.setIssuedAt( attendance.getExecutionDate() );
        attendanceDto.setDescription( attendance.getDescription() );
        attendanceDto.setResponsible( AgentDTO.serialize( attendance.getResponsible() ) );

        return attendanceDto;
    }

    public static Attendance deserialize( AttendanceDTO attendanceDto, ServiceModule module ) {

        var attendance = module.getAttendanceService()
                                .findAttendanceByProtocol( attendanceDto.protocol );

        if( attendance == null ) {

            var agentService = module.getAgentService();
            var callService = module.getCallService();

            attendance = new Attendance();
            attendance.setUserCall( callService.findCallByProtocol( attendanceDto.callProtocol ) );
            attendance.setExecutionDate( new Timestamp( System.currentTimeMillis() ) );
            attendance.setResponsible( agentService.findByUsername( attendanceDto.responsible.getUsername() ) );
        }

        if( attendanceDto.description != null )
            attendance.setDescription( attendanceDto.description );

        return attendance;
    }
}