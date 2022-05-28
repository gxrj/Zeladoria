package com.femass.authzserver.config;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import com.femass.authzserver.auth.filters.CustomLogoutFilter;
import com.femass.authzserver.auth.handlers.AuthEntryPoint;
import com.femass.authzserver.auth.services.InMemoryTokenService;
import com.femass.authzserver.utils.KeyGeneratorUtils;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenCustomizer;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/** The line bellow forces spring to not reuse AuthServerConfig beans */
@Configuration
public class AuthzServerConfig {

    @Value( "${oauth2.authorization-server-address}" )
    private String authorizationServerAddress;

    @Value( "${oauth2.client1.cors-allowed-origin}" )
    private String agentCorsAllowedOrigin;
    
    @Value( "${oauth2.client2.cors-allowed-origin}" )
    private String userCorsAllowedOrigin;

    @Autowired
    private AuthEntryPoint authEntryPoint;
    
    /**
     * Sets OAuth2AuthorizationServer filter configuration
     * @param http
     * @return AuthorizationServer filter chain
     * @throws Exception
     */
    @Bean
    @Order( Ordered.HIGHEST_PRECEDENCE )
    public SecurityFilterChain securityFilterChain( HttpSecurity http ) throws Exception {

        var authorizationServerConfigurer = authzServerConfigurer();
		RequestMatcher endpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();

		http
			.requestMatcher( endpointsMatcher )
			.authorizeRequests(
                authorizeRequests ->
                                authorizeRequests.anyRequest().authenticated()
			)
            .cors()
                .configurationSource( corsConfigSource() )
            .and()
            .csrf( csrf -> csrf.ignoringRequestMatchers( endpointsMatcher ) )
            .exceptionHandling(
                 exceptionCustomizer -> 
                    exceptionCustomizer
                        .authenticationEntryPoint( authEntryPoint )
                        .accessDeniedHandler( 
                            ( request, response, accessDeniedEx ) -> 
                                            response.sendError( 404, accessDeniedEx.getMessage() ) 
                        )
            )
			.apply( authorizationServerConfigurer );

        return http.build();
    }
    
    /**
     * Configure allowed client origins
     */
    @Bean
    public CorsConfigurationSource corsConfigSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins( List.of( agentCorsAllowedOrigin, userCorsAllowedOrigin ) );
        config.setAllowedMethods( List.of( "GET", "POST" ) );
        config.setAllowedHeaders( List.of( "*" ) );

        UrlBasedCorsConfigurationSource sourceMatcher = new UrlBasedCorsConfigurationSource();
        sourceMatcher.registerCorsConfiguration( "/**", config );
        
        return sourceMatcher;
    }

    /**
     * Exposes a customization which explicitly tells the
     * exact signature algorithm that should be used and
     * inject principal authorities
     */
    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer() {
        return context -> {

            context.getHeaders().algorithm( SignatureAlgorithm.ES256 );

            Collection<? extends GrantedAuthority> authorities = context
                                                                    .getPrincipal()
                                                                        .getAuthorities();

            var authList = authorities.stream().map( Objects::toString ).toList();
            context.getClaims().claim( "authorities", authList );
        };
    }

    /**
     * Sets token providers address
     * @return ProviderSettings instance
     */
    @Bean
    public ProviderSettings providerSettings() { 

        return ProviderSettings.builder()
                                .issuer( authorizationServerAddress )
                                .build(); 
    }

    /**
     * Generates a set of key pairs for cryptography
     * purposes ( encryption, signature) containing 
     * its specs and SecurityContext into JSON form.
     * And returns a lambda expression that retrieves
     * Json Web Keys by matching a specified selector.
     * @return JWKSource<T> implementation
     */
    @Bean
    public JWKSource<SecurityContext> jwkSource() throws JOSEException {

        var signatureKeys = List.of(
                                            KeyGeneratorUtils.getECKeys(),
                                            KeyGeneratorUtils.getRsaKeys()
                                      );

        JWKSet webKeysSet =  new JWKSet( signatureKeys );
        
        return ( jwkSelector, context ) -> jwkSelector.select( webKeysSet );
    }

    /**
     * Exposes a decoder for verifying the signature.
     * @return JwtDecoder
     */
    @Bean
    public JwtDecoder jwtDecoder( JWKSource<SecurityContext> jwkSource ) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder( jwkSource );
    }

    private OAuth2AuthorizationServerConfigurer<HttpSecurity> authzServerConfigurer() {
        return new OAuth2AuthorizationServerConfigurer<>();
    }
}