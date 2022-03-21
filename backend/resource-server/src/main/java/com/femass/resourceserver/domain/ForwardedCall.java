package com.femass.resourceserver.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Getter @Setter

@Entity( name = "OcorrenciaEncaminhada" )
public class ForwardedCall implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column( name = "id", columnDefinition = "uuid not null" )
    private UUID id;

    @ManyToOne
    @JoinColumn( name = "destino" )
    private Department destination;
    @OneToOne
    @JoinColumn( name = "atendimento" )
    private Attendance attendance;

    @Column( name = "status" )
    private ForwardStatus status;
}