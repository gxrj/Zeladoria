package com.femass.authzserver.auth.filters;

/**
 * Revokes all other access_tokens and refresh_tokens
 * when asking for new tokens right after
 * /oauth2/token endpoint gets hit
 */
public class UniqueTokenPerSessionFilter {
}
