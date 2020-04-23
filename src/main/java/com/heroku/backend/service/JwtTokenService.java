package com.heroku.backend.service;

import com.heroku.backend.exceptions.ExpiredTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
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

    public String refreshToken(String token) throws ExpiredTokenException {
        final Date createdDate = new Date();
        final Date expirationTime = calculateExpiration(createdDate);
        Claims claims;

        try{
            claims = getAllClaimsFromToken(token);
        }
        catch(ExpiredJwtException e)
        {
            throw new ExpiredTokenException();
        }


        claims.setIssuedAt(createdDate);
        claims.setExpiration(expirationTime);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String generateToken(String username){
        final Date createdDate = new Date();
        final Date expirationTime = calculateExpiration(createdDate);

        return Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(username)
                .setIssuedAt(createdDate)
                .setExpiration(expirationTime)
                .signWith(SignatureAlgorithm.HS512, secret)
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

    private Boolean isTokenNotExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.after(new Date());
    }

    public Optional<Boolean> validateToken(String token) {
        return  isTokenNotExpired(token) ? Optional.of(Boolean.TRUE) : Optional.empty();
    }

}
