
const scope = 'user'
const clientId = 'user-client'
const clientSecret = 123
const redirectUri = 'http://127.0.0.1:4200/redirection'

const OAUTH2_CLIENT_CONFIG = {

    AUTHORIZE_ENDPOINT_PARAMS: {
        user_type: 'user',
        response_type: 'code',
        scope: scope,
        client_id: clientId,
        redirect_uri: redirectUri
    },

    TOKEN_ENDPOINT_PARAMS: {
        user_type: 'user',
        grant_type: 'authorization_code',
        scope: scope,
        client_id: clientId,
        client_secret: clientSecret,
        redirect_uri: redirectUri,
    }
}

export default OAUTH2_CLIENT_CONFIG