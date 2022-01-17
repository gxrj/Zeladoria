package com.femass.authserver.config;

import java.util.UUID;

import com.femass.authserver.auth.filters.UserAuthFilter;
import com.femass.authserver.utils.KeyGeneratorUtils;
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
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.web.SecurityFilterChain;

/** the line bellow forces spring to not reuse AuthServerConfig beans */
@Configuration( proxyBeanMethods = false )

public class AuthServerConfig {

    @Value( "${oauth2.client-id}" )
    private String clientId;

    @Value( "${oauth2.client-secret}" )
    private String clientSecret;

    @Value( "${oauth2.authorization-server-address}" )
    private String authorizationServerAddress;

    /**
     * Enables HttpSecurity customization by:
     * <li>adding custom filters,</li> 
     * <li>expose custom jwt decoders,</li> 
     * <li>and set endpoint controls</li>
     * @param http
     * @return the customized security filter chain
     * @throws Exception
     */

    @Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public SecurityFilterChain authorizationServerSecurityFilterChain( HttpSecurity http )
    throws Exception {

        http.addFilter( new UserAuthFilter() );

        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity( http );

		return http.build();
	}

    /**
     * Sets information about thrusted clients
     * and how interact with them
     * @return RegisteredClientRepository instance
     */

    @Bean
    public RegisteredClientRepository createRegisteredClientsRepositoryBean(){

        var clientAddress = "http://localhost:4200/home"; //Angular client home page

        RegisteredClient client = RegisteredClient.withId( UUID.randomUUID().toString() )
                                                .clientId( clientId )
                                                .clientSecret( clientSecret )
                                                .clientAuthenticationMethod( ClientAuthenticationMethod.CLIENT_SECRET_BASIC )
                                                .authorizationGrantType( AuthorizationGrantType.PASSWORD )
                                                .authorizationGrantType( AuthorizationGrantType.REFRESH_TOKEN )
                                                .redirectUri( clientAddress )
                                                .build();

        return new InMemoryRegisteredClientRepository( client );
    }

    /**
     * Sets token providers address
     * @return ProviderSettings instance
     */

    @Bean
    public ProviderSettings createProviderSettingsBean(){ 

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
    public JWKSource<SecurityContext> getWebKeySources(){

        JWKSet webKeysSet =  new JWKSet( KeyGeneratorUtils.getECKeys() );
        
        return ( jwkSelector, context ) -> jwkSelector.select( webKeysSet );
    } 
}
