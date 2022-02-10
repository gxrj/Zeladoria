package com.femass.authzserver.config;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;

/** the line bellow forces spring to not reuse AuthServerConfig beans */

@Configuration( proxyBeanMethods = false )
public class AuthzServerConfig {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value( "${oauth2.angular.client-id}" )
    private String clientId;

    @Value( "${oauth2.angular.client-secret}" )
    private String clientSecret;

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
			.csrf(csrf -> csrf.ignoringRequestMatchers( endpointsMatcher ) )
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
			.apply(authorizationServerConfigurer);

        return http.build();
    }

    /**
     * Sets information about thrusted clients
     * and how interact with them
     * @return RegisteredClientRepository instance
     */
    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        var userClientAddress = "http://auth-server:8090/login"; //Angular client home page
        var agentClientAddress = "http://auth-server:8090/agent/login";

        var client = RegisteredClient
                        .withId( UUID.randomUUID().toString() )
                        .clientId( clientId )
                        .clientSecret( passwordEncoder.encode( clientSecret ) )
                        .clientAuthenticationMethod( ClientAuthenticationMethod.CLIENT_SECRET_POST )
                        .authorizationGrantType( AuthorizationGrantType.AUTHORIZATION_CODE )
                        .authorizationGrantType( AuthorizationGrantType.REFRESH_TOKEN )
                        .scope( "angular" )
                        .clientSettings( this.clientSettings() )
                        .tokenSettings( this.tokenSettings() )
                        .redirectUris( setConsumer -> setConsumer.addAll( List.of( userClientAddress, agentClientAddress ) ) )
                        .clientName( "angular" )
                        .build();

        
        return new InMemoryRegisteredClientRepository( client );
    }

    @Bean
    public ClientSettings clientSettings(){

        return ClientSettings.builder()
                                .requireAuthorizationConsent( false )
                                .requireProofKey( false )
                                .build();
    }

    @Bean
    public TokenSettings tokenSettings(){
        return TokenSettings.builder()
                                .accessTokenTimeToLive( Duration.ofMinutes( 15 ) )
                                .refreshTokenTimeToLive( Duration.ofMinutes( 15 ) )
                                .idTokenSignatureAlgorithm( SignatureAlgorithm.ES256 )
                                .build();
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