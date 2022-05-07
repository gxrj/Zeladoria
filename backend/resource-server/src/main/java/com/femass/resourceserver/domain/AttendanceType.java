package com.femass.resourceserver.domain;

import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

public enum AttendanceType implements Serializable {
    ANSWER( "resposta" ),
    FORWARD( "encaminhamento" );

    private final String value;

    AttendanceType( String value ) { this.value = value; }

    @JsonValue
    @Override
    public String toString() { return this.value; }

    public boolean isEqualTo( AttendanceType type ) { return this.value.equalsIgnoreCase( type.toString() );}
}
