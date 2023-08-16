package com.springgoals.filter;

import com.springgoals.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.auth0.jwt.exceptions.JWTVerificationException;

public class AuthenFilter implements Filter {

    @Autowired
    private UserServiceImpl userService ;

    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        final String jwtToken = httpServletRequest.getHeader("Authorization");

        if(  jwtToken != null ){
            try{
                if( userService.isJWTnotValidOrExpired( jwtToken) != true )
                    chain.doFilter(servletRequest, servletResponse);
            }
            catch (JWTVerificationException e) {
                System.out.println( "jwt token is not valid or expired" );
            }
            System.out.println("Error occurred: token is not valid or expired");
        }

        System.out.println( "Called Auth filter !!!" );

    }

    public void destroy() {
    }
}
