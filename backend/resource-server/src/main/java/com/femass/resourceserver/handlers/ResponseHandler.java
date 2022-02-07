package com.femass.resourceserver.handlers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public class ResponseHandler {
    

    public static void prepareJsonResponse( HttpServletResponse resp,
                                            int httpStatus, String message )
    throws IOException {

        resp.setContentType( "application/json" );
        resp.setCharacterEncoding( "utf-8" );

    }
}
