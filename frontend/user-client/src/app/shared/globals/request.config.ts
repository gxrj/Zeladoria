
const REQUEST = {
    API: {
        AUTHZ_SERVER_URL: 'http://auth-server:8090/oauth2/authorize',
        RESOURCE_SERVER_URL: 'http://auth-server:8090/oauth2/token'
    },

    HEADER: {
        FORM_CONTENT_TYPE: { Content_type: 'application/x-www-form-urlencoded', Charset: 'utf-8' },
        JSON_CONTENT_TYPE: { Content_type: 'application/json', Charset: 'utf-8' }
    }
}

export default REQUEST