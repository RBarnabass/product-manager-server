package com.product.manager.server.security.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

/**
 * JWT service intended to create and validate token
 * also gives some useful tools for token
 */
public interface JwtService {

    /**
     * Extract username from specified token
     * @param token specified token
     * @return username
     */
    String extractUsername(final String token);

    /**
     * Extract token expiration date
     * @param token specified token
     * @return expiration date
     */
    Date extractExpiration(final String token);

    /**
     * Generate new token
     * @param userDetails details for validation
     * @return new token
     */
    String generateToken(final UserDetails userDetails);

    /**
     * Check if token expiration date is valid
     * @param token specified token
     * @return true if token is valid
     */
    boolean isTokenExpired(final String token);

    /**
     * Check if token is issued for current user and not expired
     * @param token specified token
     * @param userDetails details for validation
     * @return true if token is valid
     */
    boolean validateToken(final String token, final UserDetails userDetails);
}
