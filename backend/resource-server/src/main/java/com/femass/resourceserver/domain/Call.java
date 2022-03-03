package com.femass.resourceserver.domain;

import com.femass.resourceserver.domain.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Getter
@Setter

@AllArgsConstructor
@NoArgsConstructor

@Entity( name = "Ocorrencia" )
public class Call {

    @Id
    @Column( name = "id", columnDefinition = "uuid not null" )
    @GeneratedValue( strategy = GenerationType.AUTO )
    private UUID id;

    @Column( name = "protocolo", nullable = false, length = 16 )
    private String protocol;

    @Column( name = "endereco", nullable = false )
    private Address address;

    @Column( name = "descricao")
    private String description;

    @Embedded
    private List<Image> images;

    @Column( name = "secretaria", length = 100 )
    private Department department;

    @Column( name = "autor" )
    private UserEntity author;

    @Column( name = "dt_postagem" )
    private Timestamp postingDate;

    @OneToMany( mappedBy = "userCall" )
    private List<CallResponse> callResponses;

    @Column( name = "servico" )
    private Duty duty;
}