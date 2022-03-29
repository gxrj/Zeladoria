const OAUTH_REQUEST = {

    authzServer: {
        baseUrl: 'http://auth-server:8090/',
        authorizeEndpont: 'oauth2/authorize',
        tokenEnpoint: 'oauth2/token',
        revokeEndpoint: 'oauth2/revoke'
    },
    resourceServer: { 
        baseUrl: 'http://localhost:8080' 
    },

    HEADER: {
        FORM_CONTENT_TYPE: { 
            Charset: 'utf-8',
            'Content-Type': 'application/x-www-form-urlencoded' 
        },
        JSON_CONTENT_TYPE: { 
            Charset: 'utf-8',
            'Content-Type': 'application/json'
         }
    }
}

export default OAUTH_REQUEST