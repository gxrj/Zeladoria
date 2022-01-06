package com.femass.authserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class ApplicationSecurityConfig {

    @Bean 
    SecurityFilterChain getCustomSecurityFilterChain( HttpSecurity http )
    throws Exception 
    {
        http.authorizeRequests(

            req -> req.anyRequest()
                        .authenticated()

        );

        return http.build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoderBean(){
        return new Argon2PasswordEncoder();
    }
}