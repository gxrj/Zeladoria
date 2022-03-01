package com.femass.authzserver.config;

import java.util.HashMap;
import java.util.Map;

import com.femass.authzserver.auth.filters.AgentAuthFilter;
import com.femass.authzserver.auth.filters.UserAuthFilter;
import com.femass.authzserver.auth.providers.AgentAuthProvider;
import com.femass.authzserver.auth.providers.UserAuthProvider;
import com.femass.authzserver.auth.services.AgentService;
import com.femass.authzserver.auth.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import org.springframework.web.cors.CorsConfigurationSource;

@EnableWebSecurity( debug = true )
public class ApplicationSecurityConfig {

    @Autowired
    private AgentService agentService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private CorsConfigurationSource corsConfigSource;

    @Bean
    public SecurityFilterChain appSecurityFilterChain( HttpSecurity http ) 
        throws Exception {

        http
            .authorizeHttpRequests(
                authRequests -> authRequests.mvcMatchers( 
                                                "/login", 
                                                "/agent/login",
                                                "/unauthorized", "/error",
                                                "/css/**", "/img/**"
                                            )
                                            .permitAll()
                                            .anyRequest().authenticated()
            );

        http
            .cors()
                .configurationSource( corsConfigSource )
            .and()
            .csrf().disable()
            .sessionManagement()
                .sessionCreationPolicy( SessionCreationPolicy.NEVER );

        http
            .exceptionHandling()
                .defaultAuthenticationEntryPointFor( 
                    new LoginUrlAuthenticationEntryPoint( "/login" ), 
                    new AntPathRequestMatcher( "/login" )
                )
                .defaultAuthenticationEntryPointFor(
                    new LoginUrlAuthenticationEntryPoint( "/agent/login" ), 
                    new AntPathRequestMatcher( "/agent/login" ) 
                );
        

        http
            .addFilterBefore( 
                agentAuthFilter( "/agent/login", "POST" ),
                AnonymousAuthenticationFilter.class 
            )
            .addFilterAfter( 
                userAuthFilter( "/login", "POST" ),
                AgentAuthFilter.class
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
    public AuthenticationManager authenticationManager() {

        return new ProviderManager( 
                    new AgentAuthProvider( agentService, passwordEncoder() ), 
                    new UserAuthProvider( userService, passwordEncoder() ) 
                );
    }

    /**
     * Gets a bean responsable for
     * encode users password
     * @return An encoder which chooses
     * what encode algorithm to apply  
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        var defaultEncoder = "argon2";

        Map<String, PasswordEncoder> encoders = new HashMap<>();

        encoders.put( "bcrypt", new BCryptPasswordEncoder() );
        encoders.put( "scrypt", new SCryptPasswordEncoder() );
        encoders.put( "argon2", new Argon2PasswordEncoder() );

        return new DelegatingPasswordEncoder( defaultEncoder, encoders );
    }

    private AgentAuthFilter agentAuthFilter( String url, String httpMethod ) {
        var matcher = requestMatcher( url, httpMethod );
        var agentFilter = new AgentAuthFilter( matcher, authenticationManager() );
        agentFilter.setAuthenticationFailureHandler( authFailHandler() );
        
        return agentFilter;
    }

    private UserAuthFilter userAuthFilter( String url, String httpMethod ) { 
        var matcher =  requestMatcher( url, httpMethod );
        var userFilter =  new UserAuthFilter( matcher, authenticationManager() );
        userFilter.setAuthenticationFailureHandler( authFailHandler() );

        return userFilter;
    }

    public AuthenticationFailureHandler authFailHandler() {

        Map<String, String> failures = new HashMap<>();
        failures.put( BadCredentialsException.class.getName(), "/unauthorized");
        failures.put( UsernameNotFoundException.class.getName(), "/unauthorized" );

        var handler = new ExceptionMappingAuthenticationFailureHandler();
        handler.setExceptionMappings( failures );

        return handler;
    }

    private RequestMatcher requestMatcher( String pattern, String httpMethod ){
        return new AntPathRequestMatcher( pattern, httpMethod );
    }
}
