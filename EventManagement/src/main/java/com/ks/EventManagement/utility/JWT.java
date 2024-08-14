package com.ks.EventManagement.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
@AllArgsConstructor
public class JWT {
    private final SecurityCont securityCont;
    private final static String key = "javacJWT24";

    //generate token
    private String generateToken(Authentication authentication){
        String token = "";
        try {
            token = Jwts.builder()
                    .setSubject(authentication.getName())
                    .claim("username", authentication.getName())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + SecurityCont.EXPIRATION))
                    .signWith(SignatureAlgorithm.HS256, key)
                    .compact();
        }catch (Exception e){
            System.out.println("Generation token error" + e.toString());
        }
        return token;
    }

    public Claims getClaims(String token){
        return Jwts.parser().setSigningKey(key).parseClaimsJwt(token).getBody();
    }

    public String getUsernameFromToken(String token){
        return getClaims(token).getSubject();
    }

    public boolean isValid(String token){
        return getClaims(token).getExpiration().before(new Date());
    }

    public void loginUser(String username, String password, HttpServletResponse response){
        Authentication auth = new UsernamePasswordAuthenticationToken(username, password);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String token = generateToken(auth);
        Cookie cookie = new Cookie("AUTHORIZATION", URLEncoder.encode(token, StandardCharsets.UTF_8));
        cookie.setMaxAge(10000);
        response.addCookie(cookie);
    }
}
