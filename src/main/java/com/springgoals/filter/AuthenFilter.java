package com.springgoals.filter;

import com.springgoals.security.JwtTokenUtility;
import com.springgoals.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class AuthenFilter extends OncePerRequestFilter {

    @Autowired
    private UserServiceImpl userService ;

    @Autowired
    private JwtTokenUtility jwtTokenUtilitator;

    @Override
    public void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) throws ServletException, IOException {

        System.out.println( "Called Auth filter !" );


        final String jwtToken = request.getHeader("Authorization");

        if(jwtToken == null) {
            System.out.println( "jwt token is missing" );
            chain.doFilter(request, response);
            return;
        }

        if( userService.isJWTnotValidOrExpired( jwtToken) == true ) {
            System.out.println( "jwt token is not valid or expired" );
            chain.doFilter(request, response);
            return;
        }

        String[] jwtClaims = jwtTokenUtilitator.getSubject(jwtToken).split(",");

        String emailFromToken = jwtClaims[1];

        if( emailFromToken != null) {
            System.out.println( "User is authenticated" );
        }
        chain.doFilter(request, response);

    }

}
