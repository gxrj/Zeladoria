
const REQUEST = {
    API: {
        AUTHZ_SERVER_URL: {
            AUTHORIZE_ENPOINT: 'http://auth-server:8090/oauth2/authorize',
            TOKEN_ENPOINT: 'http://auth-server:8090/oauth2/token'
        },
        RESOURCE_SERVER_URL: 'http://localhost:8080/user'
    },

    HEADER: {
        FORM_CONTENT_TYPE: { 'Content-Type': 'application/x-www-form-urlencoded', Charset: 'utf-8' },
        JSON_CONTENT_TYPE: { 'Content-Type': 'application/json', Charset: 'utf-8' }
    }
}

export default REQUEST