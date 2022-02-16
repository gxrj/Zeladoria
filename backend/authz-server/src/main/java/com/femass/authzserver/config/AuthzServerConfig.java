package com.femass.authzserver.config;

import java.util.List;

import com.femass.authzserver.auth.handlers.AuthEntryPoint;
import com.femass.authzserver.utils.KeyGeneratorUtils;
import com.femass.authzserver.utils.RequestHandler;

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
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenCustomizer;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/** the line bellow forces spring to not reuse AuthServerConfig beans */
@Configuration( proxyBeanMethods = false )
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

        OAuth2AuthorizationServerConfigurer<HttpSecurity> authorizationServerConfigurer =
				new OAuth2AuthorizationServerConfigurer<>();
		RequestMatcher endpointsMatcher = authorizationServerConfigurer
				.getEndpointsMatcher();

		http
			.requestMatcher( endpointsMatcher )
			.authorizeRequests( authorizeRequests ->
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
     * exact signature algorithm that should be used
     */
    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer(){
        return context -> context.getHeaders().algorithm( SignatureAlgorithm.ES256 );
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
     * its specs and SecutiryContext into JSON form.
     * And returns a lambda expression that retireves 
     * Json Web Keys by matching a specified selector.
     * @return JWKSource<T> implementation
     */
    @Bean
    public JWKSource<SecurityContext> getWebKeySources() {

        JWKSet webKeysSet =  new JWKSet( KeyGeneratorUtils.getECKeys() );
        
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
}
