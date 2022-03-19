package com.femass.authzserver.auth.models.domain;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.List;

public interface Account extends Serializable {

    List<? extends GrantedAuthority> getAuthorities();
    String getUsername();
    <T> T getCredentials();
    boolean getEnabled();
}
