package com.femass.resourceserver.domain;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter

@JsonRootName( value = "status" )
public enum Status {

    PROCESSING( "Em andamento" ),
    FORWARDED( "Encaminhada" ),
    ANSWERED( "Respondida" ),
    REJECTED( "Indeferida" ),
    FINISHED( "Finalizada" );

    private final String value;

    Status( String value ) {
        this.value = value;
    }

    @JsonValue
    @Override
    public String toString() {
        return this.value;
    }

    public boolean equals( String status ) {
        return this.value.equalsIgnoreCase( status );
    }
}