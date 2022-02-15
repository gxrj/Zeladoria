package com.femass.authzserver.config;

import com.femass.authzserver.utils.KeyGeneratorUtils;
import com.femass.authzserver.utils.RequestHandler;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;

/** the line bellow forces spring to not reuse AuthServerConfig beans */

@Configuration( proxyBeanMethods = false )
public class AuthzServerConfig {

    @Value( "${oauth2.authorization-server-address}" )
    private String authorizationServerAddress;

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
            .cors().disable()
			.csrf( csrf -> csrf.ignoringRequestMatchers( endpointsMatcher ) )
            .exceptionHandling(
                 exceptionCustomizer -> 
                    exceptionCustomizer
                        .authenticationEntryPoint(
                            /** 
                             * Responsable to handle which form login 
                             * will be shown according to user type 
                             * when hit /oauth2/authorized unnauthenticated
                             */
                            ( request, response, authException ) -> {

                                String userType;

                                if ( RequestHandler.isJsonContent( request ) ) 
                                    userType = RequestHandler.parseToJson( request ).get( "user_type" ).asText();
                                else
                                    userType = request.getParameter( "user_type" );

                                    
                                if( userType.equalsIgnoreCase( "agent" ) )
                                    response.sendRedirect( "/agent/login" );
                                else
                                    response.sendRedirect( "/login" );
                            } 
                        ) 
            )
			.apply( authorizationServerConfigurer );

        return http.build();
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