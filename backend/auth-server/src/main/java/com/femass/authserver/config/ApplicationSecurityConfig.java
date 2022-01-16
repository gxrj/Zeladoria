package com.femass.authserver.config;

import com.femass.authserver.auth.filters.UserAuthFilter;
import com.femass.authserver.auth.providers.CidadaoAuthProvider;
import com.femass.authserver.auth.providers.ColaboradorAuthProvider;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig {

    // @Autowired
    // private ColaboradorService colaboradorService;

    // @Autowired
    // private CidadaoService cidadaoService;

    /**
     * Builds a security filter chain with customized 
     * endpoint authorization rules.
     * @param http
     * @return the customized security filter chain
     * @throws Exception
     */
    @Bean 
    public SecurityFilterChain securityFilterChain( HttpSecurity http )
    throws Exception 
    {
        http.addFilter( new UserAuthFilter() );

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
    public AuthenticationManager authenticationManager( AuthenticationManagerBuilder auth )
    throws Exception 
    {   
        return auth
                .authenticationProvider( new ColaboradorAuthProvider() )
                .authenticationProvider( new CidadaoAuthProvider() )
                .getOrBuild();
    }

    /**
     * Gets a bean responsable for
     * encode users password
     * @return An encoder which chooses
     * what encode algorithm to apply  
     */
    @Bean
    public PasswordEncoder getPasswordEncoderBean(){
        var defaultEncoder = "argon2";

        Map<String, PasswordEncoder> encoders = new HashMap<>();

        encoders.put( "bcrypt", new BCryptPasswordEncoder() );
        encoders.put( "scrypt", new SCryptPasswordEncoder() );
        encoders.put( "argon2", new Argon2PasswordEncoder() );

        return new DelegatingPasswordEncoder( defaultEncoder, encoders );
    }
}