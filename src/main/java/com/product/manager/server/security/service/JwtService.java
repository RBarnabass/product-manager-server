package com.product.manager.server.security.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

public interface JwtService {

    String extractUsername(final String token);
    Date extractExpiration(final String token);
    String generateToken(final UserDetails userDetails);
    boolean isTokenExpired(final String token);
    boolean validateToken(final String token, final UserDetails userDetails);
}
