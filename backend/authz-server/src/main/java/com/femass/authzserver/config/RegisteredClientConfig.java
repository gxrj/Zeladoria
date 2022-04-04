package com.femass.authzserver.config;

import java.time.Duration;
import java.util.List;
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

    @Value( "${oauth2.client1.client-id}" )
    private String agentClientId;

    @Value( "${oauth2.client1.client-secret}" )
    private String agentClientSecret;

    @Value( "${oauth2.client1.redirect-uri}" )
    private String agentClientAddress;
    
    @Value( "${oauth2.client2.client-id}" )
    private String userClientId;

    @Value( "${oauth2.client2.client-secret}" )
    private String userClientSecret;
    
    @Value( "${oauth2.client2.redirect-uri}" )
    private String userClientAddress;

    @Value( "${oauth2.resource.client-id}" )
    private String resourceClientId;

    @Value( "${oauth2.resource.client-secret}" )
    private String resourceClientSecret;

    /**
     * Sets information about thrusted clients
     * and how interact with them
     * @return RegisteredClientRepository instance
     */
    @Bean
    public RegisteredClientRepository registeredClientRepository() {

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
                        .scope( "citizen" )
                        .clientSettings( this.clientSettings() )
                        .tokenSettings( this.tokenSettings() )
                        .redirectUri( userClientAddress )
                        .clientName( "citizen-client" )
                        .build();

        var resourceClient = RegisteredClient
                        .withId( UUID.randomUUID().toString() )
                        .clientId( resourceClientId )
                        .clientSecret( passwordEncoder.encode( resourceClientSecret ) )
                        .clientAuthenticationMethod( ClientAuthenticationMethod.CLIENT_SECRET_POST )
                        .authorizationGrantType( AuthorizationGrantType.CLIENT_CREDENTIALS )
                        .build();

        return new InMemoryRegisteredClientRepository( resourceClient, agentClient, userClient );
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
                                .accessTokenTimeToLive( Duration.ofSeconds( 15 ) )
                                .refreshTokenTimeToLive( Duration.ofMinutes( 20 ) )
                                .idTokenSignatureAlgorithm( SignatureAlgorithm.ES256 )
                                .build();
    }
}
