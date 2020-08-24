package com.product.manager.server.security.service.impl;

import com.product.manager.server.security.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;
import static java.lang.System.currentTimeMillis;

@Slf4j
@Service
public class JwtServiceImpl implements JwtService {

    private static final String SECRET_KEY = "secret";
    private static final long TOKEN_LIFE_TIME_IN_MILLIS = 1000 * 60 * 60;

    @Override
    public String extractUsername(final String token) {
        return extractClaims(token, Claims::getSubject);
    }

    @Override
    public Date extractExpiration(final String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    @Override
    public String generateToken(final UserDetails userDetails) {
        return createToken(userDetails.getUsername());
    }

    @Override
    public boolean isTokenExpired(final String token) {
        return extractExpiration(token).before(new Date());
    }

    @Override
    public boolean validateToken(final String token, final UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private String createToken(final String subject) {
        return Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(currentTimeMillis() + TOKEN_LIFE_TIME_IN_MILLIS))
                .signWith(HS512, SECRET_KEY)
                .compact();
    }

    private Claims extractAllClaims(final String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    private <T> T extractClaims(final String token, final Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
}
