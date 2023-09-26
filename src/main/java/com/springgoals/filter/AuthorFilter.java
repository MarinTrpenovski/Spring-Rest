package com.springgoals.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import javax.servlet.*;
import javax.servlet.Filter;
import java.io.IOException;

public class AuthorFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }



    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {


        chain.doFilter(request, response);
        System.out.println( "Called Login filter !!!" );

    }
}
