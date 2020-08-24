package com.product.manager.server.security.controller;

import com.product.manager.server.exception.AuthenticationException;
import com.product.manager.server.security.model.AuthenticationRequest;
import com.product.manager.server.security.model.AuthenticationResponse;
import com.product.manager.server.security.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.product.manager.server.exception.api.ExceptionCode.AUTHORIZATION_FAILED;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/v1/product/manager")
public class AuthenticationController {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationController(final JwtService jwtService,
                                    final UserDetailsService userDetailsService,
                                    final AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
    }


    @PostMapping("/authenticate")
    @ResponseStatus(OK)
    public AuthenticationResponse authenticate(@Validated @RequestBody final AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("Bad credential: " + e.getMessage(), AUTHORIZATION_FAILED);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtService.generateToken(userDetails);
        return new AuthenticationResponse(token);
    }
}
