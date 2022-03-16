package com.femass.resourceserver.domain;

import com.femass.resourceserver.domain.account.AbstractAccount;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter @Setter

@MappedSuperclass
public abstract class AbstractUser {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column( name = "id", columnDefinition = "uuid not null" )
    private UUID id;

    @Column( name = "nome", length = 50 )
    protected String name;
}