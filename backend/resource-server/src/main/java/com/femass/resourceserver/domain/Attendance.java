package com.femass.resourceserver.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.femass.resourceserver.domain.user.AgentEntity;

import com.nimbusds.jose.shaded.json.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Getter
@Setter

@AllArgsConstructor
@NoArgsConstructor

@JsonNaming( value = PropertyNamingStrategies.SnakeCaseStrategy.class )

@Entity( name = "Atendimento" )
public class Attendance  implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column( name = "id", columnDefinition = "uuid not null" )
    private UUID id;

    @Column( name = "dt_execucao" )
    private Timestamp executionDate;

    @Column( name = "descricao" )
    private String description;

    @ManyToOne
    @JoinColumn( name = "responsavel", referencedColumnName = "matricula" )
    private AgentEntity responsible;

    @ManyToOne
    @JoinColumn( name = "ocorrencia",  referencedColumnName = "protocolo" )
    private Call userCall;

    @OneToMany( mappedBy = "attendance" )
    private List<UserFeedback> feedbacks;

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @JsonValue
    public JSONObject toJson() {
        var json = new JSONObject();
        json.appendField( "call_protocol", userCall.getProtocol() );
        json.appendField( "description", description );
        json.appendField( "responsible", responsible );
        return json;
    }
}