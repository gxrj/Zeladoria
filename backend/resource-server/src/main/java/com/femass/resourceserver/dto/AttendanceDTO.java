package com.femass.resourceserver.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import com.femass.resourceserver.domain.Attendance;
import com.femass.resourceserver.domain.AttendanceRating;
import com.femass.resourceserver.domain.AttendanceType;
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
    private @NotEmpty CallDTO call;
    private Timestamp issuedAt;
    private @NotEmpty String description;
    private @NotEmpty AgentDTO responsible;
    private String feedback;
    private AttendanceType type;

    private AttendanceRating rating;

    @JsonProperty( "department" )
    private DepartmentDTO dept;

    @JsonValue
    public static AttendanceDTO serialize( Attendance attendance ) {

        if( attendance == null ) return null;
        
        var attendanceDto = new AttendanceDTO();
        attendanceDto.setId( attendance.getId() );
        attendanceDto.setProtocol( attendance.getProtocol() );
        attendanceDto.setCall( CallDTO.serialize( attendance.getUserCall() ) );
        attendanceDto.setIssuedAt( attendance.getExecutionDate() );
        attendanceDto.setDescription( attendance.getDescription() );
        attendanceDto.setResponsible( AgentDTO.serialize( attendance.getResponsible() ) );
        attendanceDto.setFeedback( attendance.getFeedback() );
        attendanceDto.setType( attendance.getType() );
        attendanceDto.setDept( DepartmentDTO.serialize( attendance.getDepartment() ) );
        attendanceDto.setRating( attendance.getRating() );

        return attendanceDto;
    }

    public static Attendance deserialize( AttendanceDTO attendanceDto, ServiceModule module ) {

        var attendance = module.getAttendanceService()
                                .findAttendanceByProtocol( attendanceDto.protocol );

        if( attendance == null ) {

            var agentService = module.getAgentService();
            var callService = module.getCallService();

            attendance = new Attendance();
            attendance.setProtocol( attendanceDto.protocol );
            attendance.setUserCall( callService.findCallByProtocol( attendanceDto.call.getProtocol() ) );
            attendance.setExecutionDate( new Timestamp( System.currentTimeMillis() ) );
            attendance.setResponsible( agentService.findByUsername( attendanceDto.responsible.getUsername() ) );
            attendance.setType( attendanceDto.type );
            attendance.setDepartment( attendance.getUserCall().getDestination() );
            attendance.setRating( AttendanceRating.NOT_RATED );
        }

        if( attendanceDto.feedback != null )
            attendance.setFeedback( attendanceDto.feedback );

        if( attendanceDto.description != null )
            attendance.setDescription( attendanceDto.description );

        if( attendanceDto.rating != null )
            attendance.setRating( attendanceDto.rating );

        return attendance;
    }
}