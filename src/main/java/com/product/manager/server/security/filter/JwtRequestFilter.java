package com.product.manager.server.security.filter;

import com.product.manager.server.security.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Objects.isNull;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;
import static org.springframework.util.StringUtils.isEmpty;

@Slf4j
@Service
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final String BEARER = "Bearer ";
    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtRequestFilter(final JwtService jwtService,
                            final UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest httpServletRequest,
                                    final HttpServletResponse httpServletResponse,
                                    final FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = httpServletRequest.getHeader(AUTHORIZATION_HEADER);

        if (!isEmpty(authorizationHeader) && authorizationHeader.startsWith(BEARER)) {
            final String token = extractTokenFromHeader(authorizationHeader);
            final String username = jwtService.extractUsername(token);

             if (!isEmpty(username) && isNull(getContext().getAuthentication())) {
                 final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                 if (jwtService.validateToken(token, userDetails)) {
                     setSecurityContextAuthentication(userDetails, httpServletRequest);
                 }
             }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String extractTokenFromHeader(final String authorizationHeader) {
        return authorizationHeader.substring(BEARER.length());
    }

    private void setSecurityContextAuthentication(final UserDetails userDetails, final HttpServletRequest request) {
        final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }
}
