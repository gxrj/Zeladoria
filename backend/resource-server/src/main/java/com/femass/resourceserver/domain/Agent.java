package com.femass.resourceserver.domain;

import com.femass.resourceserver.domain.account.AgentAccount;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@NoArgsConstructor

@Entity( name = "Colaborador" )
public class Agent extends AbstractUser {

    @OneToOne
    @JoinColumn( name = "colaborador", referencedColumnName = "matricula" )
    private AgentAccount account;

    @ManyToOne
    @JoinColumn( name = "secretaria", referencedColumnName = "nome" )
    private Department department;

    public Agent( String name, AgentAccount account ) {
        this.name = name;
        this.account = account;
    }
}