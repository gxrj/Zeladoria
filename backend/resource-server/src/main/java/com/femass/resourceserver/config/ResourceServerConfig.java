package com.femass.resourceserver.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@EnableWebSecurity
public class ResourceServerConfig {

    @Value( "${cors.allowed-origins}" )
    private List<String> corsAllowedOrigins;

    @Value( "${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}" )
    private String jwkSetUri;

    @Bean
    SecurityFilterChain getFilterChainBean( HttpSecurity http ) throws Exception {

        http.authorizeRequests( 

                req -> req.mvcMatchers( "/registration**", "/h2/**" ).permitAll()
                            .mvcMatchers( "/agent/**" ).hasAnyRole( "ADMIN", "AGENT" )
                            .mvcMatchers( "/manager/**" ).hasRole( "ADMIN" )
                            .mvcMatchers( "/user/**" ).hasRole( "USER" )
                            .anyRequest()
                            .authenticated() 
            )
            .csrf().disable()
            .cors()
                .configurationSource( corsConfigSource() )
            .and()
            .exceptionHandling(

                configurer -> configurer
                                .authenticationEntryPoint( 
                                    new HttpStatusEntryPoint( HttpStatus.UNAUTHORIZED ) 
                                )
            )
            .oauth2ResourceServer()
                .jwt()
                    .decoder( jwtDecoder() );

        return http.build();
    }
    
    @Bean
    public CorsConfigurationSource corsConfigSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins( this.corsAllowedOrigins );
        config.setAllowedMethods( List.of( "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS" ) );
        config.setAllowedHeaders( List.of( "*" ) );

        var sourceMatcher = new UrlBasedCorsConfigurationSource();
        sourceMatcher.registerCorsConfiguration( "/**", config );
        
        return sourceMatcher;
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        /*
         * TODO: improve to configure trusted algorithms
         *       from JWK set response, when using a
         *       production db
         */
        return NimbusJwtDecoder
            .withJwkSetUri( jwkSetUri )
            .jwsAlgorithms(
                algorithms -> {
                    algorithms.add( SignatureAlgorithm.ES256 );
                    algorithms.add( SignatureAlgorithm.RS256 );
                }
            )
            .build();
    }
}
