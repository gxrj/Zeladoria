package com.femass.resourceserver.domain;

import com.femass.resourceserver.domain.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Getter @Setter

@AllArgsConstructor @NoArgsConstructor

@Entity( name = "Feedback" )
public class UserFeedback implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column( name = "id", columnDefinition = "uuid not null" )
    private UUID id;

    @Column( name = "descricao" )
    private String description;

    @ManyToOne
    @JoinColumn( name = "cidadao", referencedColumnName = "email" )
    private UserEntity user;

    @ManyToOne
    @JoinColumn( name = "atendimento", referencedColumnName =  "id" )
    private Attendance attendance;
}