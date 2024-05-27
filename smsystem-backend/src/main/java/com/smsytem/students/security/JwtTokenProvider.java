package com.smsytem.students.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-token-expiration-time}")
    private String jwtExpiration; // Use long for milliseconds

    // Generate JWT token
    public String generateJwtToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date();
        // Parse jwtExpirationTime to get the expiration time in milliseconds
        long expirationTimeInMs = Long.parseLong(jwtExpiration);
        // Calculate the expiration date
        Date expirationDate = new Date(currentDate.getTime() + expirationTimeInMs);

        return Jwts.builder()
                .subject(username)
                .issuedAt(currentDate)
                .expiration(expirationDate)
                .signWith(key())
                .compact();
    }

    private SecretKey key() {
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(jwtSecret));
    }

    // get username from jwt token
    public String getUsername(String token) {
        Claims claims = Jwts.parser().verifyWith(key()).build().parseClaimsJws(token).getBody();
        String username = claims.getSubject();
        return username;
    }

    // validate jwt token.
    public boolean validateToken(String token) {
        Jwts.parser().setSigningKey(key()).build().parse(token);
        return true;
    }
}
