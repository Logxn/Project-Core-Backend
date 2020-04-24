package com.heroku.backend.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Function;

@Component
public class JwtTokenService {
    private String secret;
    private Long expiration;

    public JwtTokenService(@Value("${jwt.secret}") String secret, @Value("${jwt.expiration}") Long expiration) {
        this.secret = secret;
        this.expiration = expiration;
    }

    public String generateToken(String subject){
        final Date createdDate = new Date();
        final Date expirationTime = calculateExpiration(createdDate);

        return Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(subject)
                .setIssuedAt(createdDate)
                .setExpiration(expirationTime)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    private Date calculateExpiration(Date createdDate){
        return new Date(createdDate.getTime() + expiration * 10000);
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public boolean validateInviteToken(String token, String username){
        String subject = getClaimFromToken(token, Claims::getSubject);

        // Token could be access token or malformed
        if(!subject.contains("INVITE"))
            return false;

        String[] args = subject.split("-");

        // The token is for the current username
        if(args[0] == "INVITE" && args[1] == username){
            if(isTokenExpired(token))
                return false;

            return true;
        }

        return false;
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.after(new Date());
    }

    public Optional<Boolean> validateToken(String token) {
        return  isTokenExpired(token) ?  Optional.empty() : Optional.of(Boolean.TRUE);
    }

}
