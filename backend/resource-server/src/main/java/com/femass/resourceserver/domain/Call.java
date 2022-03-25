package com.femass.resourceserver.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
@Builder

@Entity( name = "Ocorrencia" )
public class Call implements Serializable {

    @Id
    @Column( name = "id", columnDefinition = "uuid not null" )
    @GeneratedValue( strategy = GenerationType.AUTO )
    private UUID id;

    @ManyToOne
    @JoinColumn( name = "servico" )
    private Duty duty;

    @Column( name = "protocolo", nullable = false, unique = true )
    private String protocol;

    @Column( name = "status" )
    private Status status;

    @Column( name = "descricao")
    private String description;

    @Embedded
    private Address address;

    @Embedded
    private List<String> images;

    @ManyToOne
    @JoinColumn( name = "usuario" )
    private Citizen author;

    @Column( name = "dt_postagem" )
    private Timestamp postingDate;

    @ManyToOne
    @JoinColumn( name = "destinatario" )
    private Department destination;

    @ToString.Exclude
    @OneToMany( mappedBy = "userCall" )
    private List<Attendance> attendances;
}