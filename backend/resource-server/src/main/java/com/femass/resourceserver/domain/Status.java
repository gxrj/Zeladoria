package com.femass.resourceserver.domain;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.io.Serializable;

@Getter

@JsonRootName( value = "status" )
public enum Status implements Serializable {

    PROCESSING( "Em andamento" ),
    FORWARDED( "Encaminhada" ),
    ANSWERED( "Respondida" ),
    NOT_SOLVED( "Nao resolvida" ),
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

    public boolean isEqualTo( Status status ) {
        return this.value.equalsIgnoreCase( status.toString() );
    }
}