package com.rockencantech.security.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FixedAuthorizationHeaderFilter extends OncePerRequestFilter {

    @Value("${rockencantech.security.fixed-authorization-value}")
    private String fixedAuthorizationValue;    

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (!fixedAuthorizationValue.equals(authorizationHeader)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(HttpHeaders.AUTHORIZATION);
            return;
        }

        filterChain.doFilter(request, response);

    }
}
