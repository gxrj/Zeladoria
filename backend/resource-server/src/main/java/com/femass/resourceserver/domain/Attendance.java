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

    @ManyToOne
    @JoinColumn( name = "ocorrencia",  referencedColumnName = "protocolo" )
    private Call userCall;

    @Column( name = "dt_execucao" )
    private Timestamp executionDate;

    @Column( name = "descricao" )
    private String description;

    @ManyToOne
    @JoinColumn( name = "responsavel" )
    private Agent responsible;

    @OneToMany( mappedBy = "attendance" )
    private List<UserFeedback> feedbacks;
}