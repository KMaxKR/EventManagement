package com.ks.EventManagement.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class JWT {

    //generate token
    private Claims extractAllClaims(String token){
        return Jwts
                .parser()
                .setSigningKey(SecurityCont.JWT_KEY)
                .parseClaimsJwt(token)
                .getBody();
    }

    private <T> T extractClaims(String token, Function<Claims, T>claimsTFunction){
        final Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    private String generateToken(Map<String, Object> extractClaims, Authentication authentication){
        return Jwts.builder()
                .setClaims(extractClaims)
                .setSubject(authentication.getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + SecurityCont.EXPIRATION))
                .signWith(SignatureAlgorithm.HS512, SecurityCont.JWT_KEY)
                .compact();
    }

    private Date extractExpiration(String token){
        return extractClaims(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public String extractUsername(String token){
        return extractClaims(token, Claims::getSubject);
    }

    public String generate(Authentication authentication){
        return generateToken(new HashMap<>(), authentication);
    }

    public boolean isValid(String token){
        final String username = extractUsername(token);
        try{
                Jwts.parser().setSigningKey(SecurityCont.JWT_KEY).parseClaimsJwt(token);
                return true;
        }catch (AuthenticationCredentialsNotFoundException e){
            throw new AuthenticationCredentialsNotFoundException("AuthenticationCredentialsNotFound Error");
        }
    }
}
