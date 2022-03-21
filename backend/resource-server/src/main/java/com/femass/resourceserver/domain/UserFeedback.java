package com.femass.resourceserver.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder

@Entity( name = "Feedback" )
public class UserFeedback implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column( name = "id", columnDefinition = "uuid not null" )
    private UUID id;

    @Column( name = "descricao" )
    private String description;

    @ManyToOne
    @JoinColumn( name = "cidadao" )
    private Citizen user;

    @ManyToOne
    @JoinColumn( name = "atendimento" )
    private Attendance attendance;
}