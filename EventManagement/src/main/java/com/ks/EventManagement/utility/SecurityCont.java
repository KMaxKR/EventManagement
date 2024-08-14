package com.ks.EventManagement.utility;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;

@Component
public class SecurityCont {
    public final static long EXPIRATION = 6000;
    private final static String key = "6a617661634a575432346a617661634a575432346a617661634a575432346a617661634a575432346a617661634a575432346a617661634a575432346a617661634a575432346a617661634a575432346a617661634a575432346a617661634a575432346a617661634a575432346a617661634a575432346a617661634a575432346a617661634a575432346a617661634a575432346a617661634a575432346a617661634a575432346a617661634a575432346a617661634a575432346a617661634a575432346a617661634a575432346a617661634a575432346a617661634a575432346a617661634a575432346a617661634a575432346a617661634a575432346a617661634a575432346a617661634a575432346a617661634a575432346a617661634a575432346a617661634a575432346a617661634a575432346a617661634a575432346a617661634a57543234";

    private RSAPrivateKey privateKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        RSAPrivateKeySpec rsaspec = new RSAPrivateKeySpec(
                new BigInteger(key, 16),
                new BigInteger(key, 16)
        );

        return (RSAPrivateKey) keyFactory.generatePrivate(rsaspec);
    }

    public SecretKey getKey() {
        try {
            return (SecretKey) privateKey();
        }catch (NoSuchAlgorithmException | InvalidKeySpecException exception){
            System.out.println(exception.getMessage());
            return null;
        }
    }
}
