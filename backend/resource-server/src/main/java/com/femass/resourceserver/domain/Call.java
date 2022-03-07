package com.femass.resourceserver.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.femass.resourceserver.domain.user.UserEntity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Getter
@Setter

@AllArgsConstructor

@Entity( name = "Ocorrencia" )
public class Call {

    @Id
    @Column( name = "id", columnDefinition = "uuid not null" )
    @GeneratedValue( strategy = GenerationType.AUTO )
    private UUID id;

    @Setter( AccessLevel.NONE )
    @Column( name = "protocolo", nullable = false, unique = true ) @NotNull
    private String protocol;

    @Embedded
    private Address address;

    @Column( name = "descricao")
    private String description;

    @Embedded
    private List<Image> images;

    @ManyToOne
    @JoinColumn( name = "usuario", referencedColumnName = "email" )
    private UserEntity author;

    @JsonProperty( "created_at" )
    @Column( name = "dt_postagem" )
    private Timestamp postingDate;

    @OneToMany( mappedBy = "userCall" )
    private List<Attendance> attendances;

    @ManyToOne
    @JoinColumn( name = "servico", referencedColumnName = "descricao" )
    private Duty duty;

    @Column( name = "status" )
    private Status status;

    public Call() {
        var time = System.currentTimeMillis();
        this.protocol = String.format( "%d%d",time, hashCode() );
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}