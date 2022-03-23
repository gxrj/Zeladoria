package com.femass.resourceserver.domain;

import com.femass.resourceserver.domain.account.Account;
import javax.persistence.*;

@Inheritance( strategy = InheritanceType.TABLE_PER_CLASS )
public abstract class AbstractUser<T extends Account> {

    protected String name;

    protected T account;
}