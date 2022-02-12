package com.femass.authzserver.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.springframework.util.StreamUtils;

public class CachedRequest extends HttpServletRequestWrapper {
    
    private byte [] cachedBody;

    public CachedRequest( HttpServletRequest request ) throws IOException {

        super( request );
        this.cachedBody = StreamUtils.copyToByteArray( request.getInputStream() );
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new CachedBody( this.cachedBody );
    }

    @Override
    public BufferedReader getReader() throws IOException {
        var inputStream = new ByteArrayInputStream( this.cachedBody );
        return new BufferedReader( new InputStreamReader( inputStream ) );
    }
}
