package com.femass.resourceserver.domain;

import com.femass.resourceserver.domain.account.CitizenAccount;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter @Setter
@NoArgsConstructor
@ToString

@Entity( name = "Cidadao" )
public class Citizen extends AbstractUser {

    @OneToOne
    @JoinColumn( name = "cidadao", referencedColumnName = "email" )
    private CitizenAccount account;

    public Citizen( String name, CitizenAccount account ) {
        this.name = name;
        this.account = account;
    }
}