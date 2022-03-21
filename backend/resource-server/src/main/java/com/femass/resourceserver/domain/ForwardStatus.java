package com.femass.resourceserver.domain;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

import java.io.Serializable;

@Getter

@JsonRootName( value = "status" )
public enum ForwardStatus implements Serializable {

    ANSWERED( "Atendido" ),
    OPEN( "Em Aberto" );

    private String value;

    ForwardStatus( String value ) { this.value = value; }

    @JsonValue
    @Override
    public String toString() {
        return this.value;
    }

    public boolean equals( String status ) {
        return this.value.equalsIgnoreCase( status );
    }
}