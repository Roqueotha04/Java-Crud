package com.practice.api.utils;

import com.practice.api.service.security.UserDetailServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String privateKey;

    public String generateToken(Authentication authentication){
        SecretKey key = getSigningKey();

        String username = authentication.getName();
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        String jwtToken= Jwts.builder()
                .issuer("my-api")
                .subject(username)
                .claim("authorities",authorities)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1800000))
                .signWith(key)
                .compact();

        return jwtToken;
    }

    public Claims verifyToken(String jwtToken){
        SecretKey key = getSigningKey();

        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload();

        return claims;
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(privateKey));
    }

}
