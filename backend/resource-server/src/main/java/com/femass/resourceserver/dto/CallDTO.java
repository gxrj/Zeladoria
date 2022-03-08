package com.femass.resourceserver.dto;

import com.femass.resourceserver.domain.Address;
import com.femass.resourceserver.domain.Call;
import com.femass.resourceserver.domain.Duty;
import com.femass.resourceserver.domain.Status;
import com.femass.resourceserver.domain.user.UserEntity;
import com.femass.resourceserver.services.CallService;
import com.femass.resourceserver.services.DutyService;
import com.femass.resourceserver.services.UserService;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Getter
@Setter

@Component
public class CallDTO implements Serializable {

    @Getter( AccessLevel.NONE ) @Setter( AccessLevel.NONE )
    private final CallService callService;

    @Getter( AccessLevel.NONE ) @Setter( AccessLevel.NONE )
    private final UserService userService;

    @Getter( AccessLevel.NONE ) @Setter( AccessLevel.NONE )
    private final DutyService dutyService;

    private UUID id;
    private Duty duty;
    @NotNull
    private String protocol;
    private Status status;
    private String description;
    private Address address;
    private List<String> images;
    @NotNull
    private UserEntity author;
    private Timestamp createdAt;

    public CallDTO ( CallService callService,
                     UserService userService, DutyService dutyService ) {

        this.callService = callService;
        this.userService = userService;
        this.dutyService = dutyService;
    }

    public Call toDomainObject() {

        Call object;

        if( id != null )
            object = callService.findCallById( id );
        else
            object = new Call();

        object.setDuty( dutyService.findDutyByDescription( description ) );
        object.setStatus( status );
        object.setDescription( description );
        object.setAddress( address );
        object.setImages( images );
        object.setAuthor( userService.findByUsername( author.getUsername() ) );
        object.setPostingDate( createdAt );
        
        return object;
    }
}