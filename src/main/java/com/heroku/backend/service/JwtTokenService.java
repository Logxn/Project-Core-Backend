package com.heroku.backend.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;

@Component
public class JwtTokenService {
    private String secret;
    private Long expiration;

    public JwtTokenService(@Value("${jwt.secret}") String secret, @Value("${jwt.expiration}") Long expiration) {
        this.secret = secret;
        this.expiration = expiration;
    }

    public String generateToken(String username){
        final Date createdDate = new Date();
        final Date expirationTime = calculateExpiration(createdDate);

        return Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(username)
                .setIssuedAt(createdDate)
                .setExpiration(expirationTime)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    private Date calculateExpiration(Date createdDate){
        return new Date(createdDate.getTime() + expiration * 10000);
    }
}
