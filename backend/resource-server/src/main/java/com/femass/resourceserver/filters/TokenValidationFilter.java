package com.femass.resourceserver.filters;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.AccessDeniedException;

/**
 * Performs token introspection when
 * an authenticated endpoint gets hit.
 */
@Component
public class TokenValidationFilter extends OncePerRequestFilter {

    @Value( "${spring.security.oauth2.client.provider.authz-server.introspect}" )
    private String introspectEndpoint;

    @Value( "${spring.security.oauth2.client.registration.authz-server.client-id}" )
    private String clientId;

    @Value( "${spring.security.oauth2.client.registration.authz-server.client-secret}" )
    private String clientSecret;

    @Override
    public void doFilterInternal ( HttpServletRequest request,
                                    HttpServletResponse response , FilterChain chain )
                throws ServletException {

        try {

            var token = request.getHeader( "authorization" );

            // For endpoints that do not require Authorization header claim
            if (token == null) {
                chain.doFilter(request, response);
                return;
            }

            var result = performTokenIntrospection( token );

            // Parses authz-server http response into json
            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree( result.body() );

            // Checks token validation
            if ( json.get( "active" ).asBoolean( false ) ) {
                chain.doFilter(request, response);
            } else {
                prepareRejectionResponse( response );
            }
        }
        catch ( Exception ex ) {
            throw new ServletException( ex );
        }
    }

    private HttpResponse<String> performTokenIntrospection( String token )
                    throws IOException, InterruptedException, NullPointerException, URISyntaxException {

        var uri = new URI( introspectEndpoint );

        // Prepares request to authz server introspection endpoint
        var body = String.format( "token=%s&client_id=%s&client_secret=%s",
                token.replaceAll( "[Bb][Ee][Aa][Rr][Ee][Rr] ", "" ),
                clientId, clientSecret);

        var req = HttpRequest
                .newBuilder()
                .uri(uri)
                .headers(
                        "content-type", MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                        "authorization", token
                )
                .POST( HttpRequest.BodyPublishers.ofString( body ) )
                .build();

        // Performs token introspection and returns its result
        return HttpClient.newHttpClient()
                            .send( req, HttpResponse.BodyHandlers.ofString() );
    }

    private void prepareRejectionResponse( HttpServletResponse response ) throws IOException {

        response.setStatus( HttpStatus.UNAUTHORIZED.value() );
        response.setContentType( MediaType.APPLICATION_JSON_VALUE );

        var writer = response.getWriter();
        writer.print( "{\"message\": \"invalid token\"}" );
        writer.close();
    }
}