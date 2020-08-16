package com.product.manager.server.security.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
@Primary
@Service
public class CustomUserDetailServiceImpl implements UserDetailsService {

    private static final String SUPER_USERNAME = "Admin";
    private static final String SUPER_USER_PASS = "admin";

    @Override
    public UserDetails loadUserByUsername(final String username) {
        return new User(SUPER_USERNAME, SUPER_USER_PASS, new ArrayList<>());
    }
}
