package com.femass.authserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class ApplicationSecurityConfig {

    /**
     * Builds a security filter chain with customized 
     * endpoint authorization rules.
     * @param http
     * @return the customized security filter chain
     * @throws Exception
     */
    @Bean 
    public SecurityFilterChain getCustomSecurityFilterChain( HttpSecurity http )
    throws Exception 
    {
        http.authorizeRequests(

            req -> req.anyRequest()
                        .authenticated()

        );

        return http.build();
    }

    /**
     * Sets application authentication providers
     * for Cidadao and Colaborador users then returns
     * an AuthenticationManager Bean
     * @param AuthenticationManagerBuilder auth
     * @return an AuthenticationManager bean
     * @throws Exception
     */
    @Bean
    public AuthenticationManager configureAuthenticationManager( AuthenticationManagerBuilder auth )
    throws Exception 
    {   
        /**
         * auth.userDetailsService()
         * or
         * auth
         * .authenticationProvider( provider1 )
         * .authenticationProvider( provider2 ) 
        */
        
        return auth.getOrBuild();
    }

    /**
     * Gets a bean responsable for
     * encode users password
     * @return Argon2 password encoder algorithm 
     */
    @Bean
    public PasswordEncoder getPasswordEncoderBean(){
        return new Argon2PasswordEncoder();
    }
}