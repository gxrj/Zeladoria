package com.femass.authzserver.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.femass.authzserver.auth.filters.AgentAuthFilter;
import com.femass.authzserver.auth.filters.UserAuthFilter;
import com.femass.authzserver.auth.providers.AgentAuthProvider;
import com.femass.authzserver.auth.providers.UserAuthProvider;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@EnableWebSecurity( debug = true )
public class ApplicationSecurityConfig {

    @Bean
    public SecurityFilterChain appSecurityFilterChain( HttpSecurity http ) 
        throws Exception {

        http
            .authorizeHttpRequests(
                authRequests -> authRequests.mvcMatchers( "/login", "/agent/login", "/css/**", "/img/**" )
                                            .permitAll()
                                            .anyRequest().authenticated()
            );

        http
            .cors().disable()
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
                    new AgentAuthProvider( userDetailsService(), passwordEncoder() ), 
                    new UserAuthProvider( userDetailsService(), passwordEncoder() ) 
                );
    }

    @Bean
    public UserDetailsService userDetailsService() {

        var encoder = passwordEncoder();

        var agent = User.builder()
                        .username( "agent" )
                        .password( encoder.encode( "123" ) )
                        .authorities( List.of() )
                        .roles( "AGENT", "ADMIN" )
                        .build();

        var user = User.builder()
                        .username( "user" )
                        .password( encoder.encode( "123" ) )
                        .authorities( List.of() )
                        .roles( "USER" )
                        .build();

        return new InMemoryUserDetailsManager( agent, user );
    }

    /**
     * Gets a bean responsable for
     * encode users password
     * @return An encoder which chooses
     * what encode algorithm to apply  
     */
    @Bean
    public static PasswordEncoder passwordEncoder() {
        var defaultEncoder = "argon2";

        Map<String, PasswordEncoder> encoders = new HashMap<>();

        encoders.put( "bcrypt", new BCryptPasswordEncoder() );
        encoders.put( "scrypt", new SCryptPasswordEncoder() );
        encoders.put( "argon2", new Argon2PasswordEncoder() );

        return new DelegatingPasswordEncoder( defaultEncoder, encoders );
    }

    private AgentAuthFilter agentAuthFilter( String url, String httpMethod ) {
        return new AgentAuthFilter( 
                        requestMatcher( url, httpMethod ), 
                        authenticationManager() 
                    );
    }

    private UserAuthFilter userAuthFilter( String url, String httpMethod ) {    
        return new UserAuthFilter( 
                        requestMatcher( url, httpMethod ), 
                        authenticationManager() 
                    );
    }

    private RequestMatcher requestMatcher( String pattern, String httpMethod ){
        return new AntPathRequestMatcher( pattern, httpMethod );
    }
}