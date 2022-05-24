const scope = 'agent'
const clientId = 'agent-client'
const clientSecret = 123
const clientBaseUrl = 'http://127.0.0.1:8100'

const redirectUri = clientBaseUrl + '/redirection'

const OAUTH_CLIENT_CONFIG = {

    AUTHORIZE_ENDPOINT_PARAMS: {
        user_type: 'agent',
        response_type: 'code',
        scope: scope,
        client_id: clientId,
        redirect_uri: redirectUri
    },

    TOKEN_ENDPOINT_PARAMS: {
        grant_type: 'authorization_code',
        scope: scope,
        client_id: clientId,
        client_secret: clientSecret,
        redirect_uri: redirectUri,
    },

    REVOKE_ENDPOINT_PARAMS: {
        client_id: clientId,
        client_secret: clientSecret
    },

    CLIENT_BASE_URL: clientBaseUrl
}

export default OAUTH_CLIENT_CONFIG