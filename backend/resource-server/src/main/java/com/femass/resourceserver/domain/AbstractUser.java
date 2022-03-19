package com.femass.resourceserver.domain;

import com.femass.resourceserver.domain.account.Account;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter

@Inheritance( strategy = InheritanceType.TABLE_PER_CLASS )
public abstract class AbstractUser<T extends Account> {

    @Column( name = "nome", length = 50 )
    protected String name;

    protected T account;
}