package com.femass.authzserver.utils;

import java.util.UUID;

import com.nimbusds.jose.Algorithm;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.gen.ECKeyGenerator;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;

public final class KeyGeneratorUtils {
    
    private KeyGeneratorUtils() {}


    /**
     * Generate an Elliptic Curve key pair
     * and returns it.
     * @return ECKey ellipicCurveKeys
     */
    public static JWK getECKeys(){
        try{
            return new ECKeyGenerator( Curve.P_256 )
                            .keyID( UUID.randomUUID().toString() )
                            .generate();
        }
        catch( Exception ex ){
            throw new IllegalStateException();
        }
    }

    /**
     * Generate a Rsa key pair and returns it.
     * @return JWK using Rsa algorithm
     * @throws JOSEException
     */
    public static JWK getRsaKeys() throws JOSEException {

        return new RSAKeyGenerator( 2048 )
                .keyID( UUID.randomUUID().toString() )
                .keyUse( KeyUse.SIGNATURE )
                .algorithm(
                        new Algorithm( SignatureAlgorithm
                                                .RS256.toString() )
                )
                .generate();
    }
}
