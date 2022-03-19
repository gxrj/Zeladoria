package com.femass.resourceserver.domain.account;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.List;

public interface Account extends Serializable {

    abstract List<? extends GrantedAuthority> getAuthorities();
    abstract String getUsername();
    abstract <T> T getCredentials();
}