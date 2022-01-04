package com.femass.resourceserver.domain.model;

import java.util.List;

import com.femass.resourceserver.domain.abstracts.Conta;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUserWrapper implements UserDetails {

    private final Conta userAccount;

    public SecurityUserWrapper( Conta userAccount ){ this.userAccount = userAccount; }
    
    @Override
    public List <GrantedAuthority> getAuthorities(){ return List.of( () -> " " ); }

    @Override
    public String getUsername(){ return userAccount.getLogin(); }

    @Override
    public String getPassword(){ return userAccount.getSenha(); }

    @Override
    public boolean isAccountNonExpired(){ return true; }

    @Override
    public boolean isAccountNonLocked(){ return true; }

    @Override
    public boolean isCredentialsNonExpired(){ return true; }

    @Override
    public boolean isEnabled(){ return userAccount.getContaAtivada(); }
}