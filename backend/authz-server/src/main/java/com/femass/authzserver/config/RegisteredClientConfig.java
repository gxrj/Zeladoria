package com.femass.authzserver.config;

import java.time.Duration;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;

@Configuration
public class RegisteredClientConfig {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value( "${oauth2.angular.client-id}" )
    private String agentClientId;

    @Value( "${oauth2.angular.client-secret}" )
    private String agentClientSecret;

    @Value( "${oauth2.ionic.client-id}" )
    private String userClientId;

    @Value( "${oauth2.ionic.client-secret}" )
    private String userClientSecret;


    /**
     * Sets information about thrusted clients
     * and how interact with them
     * @return RegisteredClientRepository instance
     */
    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        var userClientAddress = "http://localhost:4200/login"; //Ionic client home page
        var agentClientAddress = "http://localhost:4200/agent/login"; //Angular client home page

        var agentClient = RegisteredClient
                        .withId( UUID.randomUUID().toString() )
                        .clientId( agentClientId )
                        .clientSecret( passwordEncoder.encode( agentClientSecret ) )
                        .clientAuthenticationMethod( ClientAuthenticationMethod.CLIENT_SECRET_POST )
                        .authorizationGrantType( AuthorizationGrantType.AUTHORIZATION_CODE )
                        .authorizationGrantType( AuthorizationGrantType.REFRESH_TOKEN )
                        .scope( "agent" )
                        .clientSettings( this.clientSettings() )
                        .tokenSettings( this.tokenSettings() )
                        .redirectUri( agentClientAddress )
                        .clientName( "agent-client" )
                        .build();

        var userClient = RegisteredClient
                        .withId( UUID.randomUUID().toString() )
                        .clientId( userClientId )
                        .clientSecret( passwordEncoder.encode( userClientSecret ) )
                        .clientAuthenticationMethod( ClientAuthenticationMethod.CLIENT_SECRET_POST )
                        .authorizationGrantType( AuthorizationGrantType.AUTHORIZATION_CODE )
                        .authorizationGrantType( AuthorizationGrantType.REFRESH_TOKEN )
                        .scope( "user" )
                        .clientSettings( this.clientSettings() )
                        .tokenSettings( this.tokenSettings() )
                        .redirectUri( userClientAddress )
                        .clientName( "user-client" )
                        .build();

        
        return new InMemoryRegisteredClientRepository( agentClient, userClient );
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
}
