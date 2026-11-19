package com.harsh.research.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public class JwtService {

    private final String secret = "djhfjjdnkcjsdowneowoundsljsdnjksdjcowejnuiuncjskdjosndosdcc";

    private SecretKey key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public String generateAccessToken(UUID userId, String email, String role ){

        Instant now = Instant.now();

        return Jwts.builder()
                .subject(userId.toString())
                .claim("email",email)
                .claim("role",role)
                .id(UUID.randomUUID().toString())
                .issuedAt(Date.from(now))
                .expiration(Date.from(
                        now.plus(Duration.ofHours(1))
                )).signWith(key())
                .compact();
    }

    public Optional<AuthPrincipal> parse(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(key())
                    .build().parseSignedClaims(token)
                    .getPayload();

            UUID userId = UUID.fromString(claims.getSubject());
            String email =claims.get("email",String.class);
            String role =claims.get("role",String.class);

            return Optional.of(new AuthPrincipal(userId,email,role));

        } catch (JwtException e) {
            return Optional.empty();
        }
    }

}
