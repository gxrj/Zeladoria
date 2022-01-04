package com.femass.resourceserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@EnableWebSecurity
public class ResourceServerConfig {

    @Bean
    SecurityFilterChain getFilterChainBean( HttpSecurity http ) throws Exception{

        http.authorizeRequests( 

                req -> req.mvcMatchers( "/account/auth", "/account/new" )
                            .permitAll()
                            .anyRequest()
                            .authenticated() 
            )
            .exceptionHandling(

                ex -> ex.authenticationEntryPoint( 
                            new HttpStatusEntryPoint( HttpStatus.UNAUTHORIZED ) 
                        )
            )
            .oauth2ResourceServer()
            .jwt();

        return http.build();
    }
}