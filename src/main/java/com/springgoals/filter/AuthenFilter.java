package com.springgoals.filter;

import com.springgoals.security.JwtTokenUtility;
import com.springgoals.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.springframework.stereotype.Component;

@Component
public class AuthenFilter implements javax.servlet.Filter {

    @Autowired
    private UserServiceImpl userService ;

    @Autowired
    private JwtTokenUtility jwtTokenUtilitator;

    @Override
    public void init(FilterConfig filterconfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        System.out.println( "Called AuthFilter: header Authorization : " + httpServletRequest.getHeader("Authorization") );

        if( httpServletRequest.getServletPath().contains("/api/user/login") ) {
            System.out.println( "request from login url" );

            return;
        }

        final String jwtToken = httpServletRequest.getHeader("Authorization");

        System.out.println( "jwt token in AuthFilter = " + jwtToken );

        if(jwtToken == null) {
            System.out.println( "jwt token is missing" );

            return;
        }

        if( userService.isJWTnotValidOrExpired( jwtToken) ) {
            System.out.println( "jwt token is not valid or expired" );

            return;
        }

        String[] jwtClaims = jwtTokenUtilitator.getSubject(jwtToken).split(",");

        String emailFromToken = jwtClaims[1];

        if( emailFromToken != null) {
            System.out.println( "User is authenticated" );
        }
        chain.doFilter(httpServletRequest, httpServletResponse);

    }

    @Override
    public void destroy() {}

}
