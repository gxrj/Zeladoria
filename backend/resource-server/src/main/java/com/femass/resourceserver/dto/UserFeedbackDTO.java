package com.femass.resourceserver.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import com.femass.resourceserver.domain.UserFeedback;
import com.femass.resourceserver.services.ServiceModule;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

import java.io.Serializable;
import java.util.UUID;

@Getter @Setter

@JsonNaming( value = PropertyNamingStrategies.SnakeCaseStrategy.class )
@JsonInclude( JsonInclude.Include.NON_EMPTY )
public class UserFeedbackDTO implements Serializable {

    private @NotEmpty UUID id;
    private String description;
    private @NotEmpty CitizenDTO user;
    private @NotEmpty AttendanceDTO attendance;

    @JsonValue
    public static UserFeedbackDTO serialize( UserFeedback feedback ) {

        if( feedback == null ) return null;

        var feedbackDTO = new UserFeedbackDTO();

        feedbackDTO.setDescription( feedback.getDescription() );
        feedbackDTO.setUser( CitizenDTO.serialize( feedback.getUser() ) );
        feedbackDTO.setAttendance( AttendanceDTO.serialize( feedback.getAttendance() ) );

        return feedbackDTO;
    }

    private static UserFeedback deserialize( UserFeedbackDTO feedbackDto, ServiceModule module ) {
        var feedback = module.getUserFeedbackService().findUserFeedbackById( feedbackDto.id );

        if( feedback == null ){
            feedback = new UserFeedback();
            feedback.setUser( CitizenDTO.deserialize( feedbackDto.user, module ) );
            feedback.setAttendance( AttendanceDTO.deserialize( feedbackDto.attendance, module ) );
        }
        feedback.setDescription( feedbackDto.description );

        return feedback;
    }
}