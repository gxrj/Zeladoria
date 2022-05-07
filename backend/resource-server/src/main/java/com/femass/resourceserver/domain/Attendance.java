package com.femass.resourceserver.domain;

import lombok.*;

import javax.persistence.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder

@Entity( name = "Atendimento" )
public class Attendance implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column( name = "id", columnDefinition = "uuid not null" )
    private UUID id;

    @Column( name = "protocolo", nullable = false, unique = true )
    private String protocol;

    @ManyToOne
    @JoinColumn( name = "ocorrencia",  referencedColumnName = "protocolo" )
    private Call userCall;

    @Column( name = "dt_execucao" )
    private Timestamp executionDate;

    @Column( name = "tipo_atendimento" )
    private AttendanceType type;

    @Column( name = "descricao", nullable = false )
    private String description;

    @ManyToOne
    @JoinColumn( name = "responsavel" )
    private Agent responsible;

    @Column( name = "feedback_cidadao" )
    private String feedback;
}