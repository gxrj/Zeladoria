package com.femass.resourceserver.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;

public class CachedBody extends ServletInputStream {
    
    private InputStream content;

    public CachedBody( byte[] cachedBody ){
        this.content = new ByteArrayInputStream( cachedBody );
    }

    @Override
    public int read() throws IOException {
        return this.content.read();
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public boolean isFinished() {
        try {
            return this.content.available() == 0;
        }
        catch( IOException ex ) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public void setReadListener( ReadListener listener ) {
        throw new UnsupportedOperationException();
    }
}