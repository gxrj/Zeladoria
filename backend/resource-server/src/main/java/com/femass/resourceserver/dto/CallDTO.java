package com.femass.resourceserver.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.femass.resourceserver.domain.Address;
import com.femass.resourceserver.domain.Duty;
import com.femass.resourceserver.domain.Status;
import com.femass.resourceserver.domain.user.UserEntity;

import lombok.Getter;
import lombok.Setter;

import lombok.ToString;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString

@JsonNaming( value = PropertyNamingStrategies.SnakeCaseStrategy.class )

@Component
public class CallDTO implements Serializable {

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
}