package com.femass.authzserver.auth.services;

import org.springframework.lang.Nullable;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthorizationCode;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.OAuth2TokenType;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.util.Assert;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryTokenService implements OAuth2AuthorizationService {
    private static final Map<String, OAuth2Authorization> authorizations = new ConcurrentHashMap<>();

    public InMemoryTokenService() {
        this( Collections.emptyList() );
    }

    public InMemoryTokenService( OAuth2Authorization... authorizations ) {
        this( Arrays.asList( authorizations ) );
    }

    public InMemoryTokenService( List<OAuth2Authorization> authorizations ) {
        Assert.notNull( authorizations, "authorizations cannot be null" );

        authorizations.forEach(
                authorization -> {
                    Assert.notNull( authorization, "authorization cannot be null" );
                    Assert.isTrue( !InMemoryTokenService.authorizations.containsKey( authorization.getId() ),
                            "The authorization has to be unique. Found duplicate id: " + authorization.getId() );

                    InMemoryTokenService.authorizations.put( authorization.getId(), authorization );
                }
        );
    }

    @Override
    public void save( OAuth2Authorization authorization ) {
        Assert.notNull( authorization, "authorization cannot be null" );
        authorizations.put( authorization.getId(), authorization );
    }

    @Override
    public void remove( OAuth2Authorization authorization ) {
        Assert.notNull( authorization, "authorization cannot be null" );
        authorizations.remove( authorization.getId(), authorization );
    }

    @Nullable
    @Override
    public OAuth2Authorization findById( String id ) {
        Assert.hasText( id, "id cannot be empty" );
        return authorizations.get( id );
    }

    @Nullable
    @Override
    public OAuth2Authorization findByToken( String token, @Nullable OAuth2TokenType tokenType ) {
        Assert.hasText( token, "token cannot be empty" );

        for( OAuth2Authorization authorization : authorizations.values() ) {
            if( hasToken( authorization, token, tokenType ) )
                return authorization;
        }
        return null;
    }

    public List<OAuth2Authorization> findByPrincipalName( String principalName ) {

        List<OAuth2Authorization> list = new ArrayList<>();

        for( OAuth2Authorization auth : authorizations.values() ){
            if( auth.getPrincipalName().equals( principalName ) ){
                list.add( auth );
            }
        }
        return list;
    }

    private static boolean hasToken( OAuth2Authorization authorization,
                                     String token, @Nullable OAuth2TokenType tokenType ) {

        if( tokenType == null ) {
            return matchesState( authorization, token )
                    || matchesAuthorizationCode( authorization, token )
                    || matchesAccessToken( authorization, token )
                    || matchesRefreshToken( authorization, token );
        }

        var tokenTypeValue = tokenType.getValue();

        if( OAuth2ParameterNames.STATE.equals( tokenTypeValue ) ) {
            return matchesState( authorization, token );
        } else if( OAuth2ParameterNames.CODE.equals( tokenTypeValue ) ) {
            return matchesAuthorizationCode( authorization, token );
        } else if( OAuth2ParameterNames.ACCESS_TOKEN.equals( tokenTypeValue ) ) {
            return matchesAccessToken( authorization, token );
        } else if( OAuth2ParameterNames.REFRESH_TOKEN.equals( tokenTypeValue ) ) {
            return matchesRefreshToken( authorization, token );
        }

        return false;
    }

    private static boolean matchesState( OAuth2Authorization authorization, String token ) {
        return token.equals( authorization.getAttribute( OAuth2ParameterNames.STATE ) );
    }

    private static boolean matchesAuthorizationCode( OAuth2Authorization authorization, String token ) {
        OAuth2Authorization
                .Token<OAuth2AuthorizationCode> authorizationCode = authorization
                .getToken( OAuth2AuthorizationCode.class );
        return authorizationCode != null && authorizationCode.getToken().getTokenValue().equals( token );
    }

    private static boolean matchesAccessToken( OAuth2Authorization authorization, String token ) {
        OAuth2Authorization.Token<OAuth2AccessToken> accessToken =  authorization.getAccessToken();
        return accessToken != null && accessToken.getToken().getTokenValue().equals( token );
    }

    private static boolean matchesRefreshToken( OAuth2Authorization authorization, String token ) {
        OAuth2Authorization.Token<OAuth2RefreshToken> refreshToken = authorization.getRefreshToken();
        return refreshToken != null && refreshToken.getToken().getTokenValue().equals( token );
    }
}