package com.femass.resourceserver.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import com.nimbusds.jose.shaded.json.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter

@AllArgsConstructor
@NoArgsConstructor

@Entity( name = "Servico" )
public class Duty implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column( name = "id", columnDefinition = "uuid not null" )
    private UUID id;

    @NotNull
    @Column( name = "descricao", nullable = false, unique = true )
    private String description;

    @ManyToOne
    @JoinColumn( name = "secretaria", referencedColumnName = "nome" )
    private Department department;

    @JsonValue
    public JSONObject toJson(){
        var json = new JSONObject();
        json.appendField( "description", description );
        json.appendField( "department", department );
        return json;
    }
}