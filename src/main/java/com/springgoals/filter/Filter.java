package com.springgoals.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
public class Filter implements javax.servlet.Filter {

    @Override
    public void init(FilterConfig filterconfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {

        System.out.println("Remote Host:"+req.getRemoteHost() );
        System.out.println("Remote Host:"+req.getRemoteAddr() );
        chain.doFilter(req, resp);//sends request to next resource


    }

    @Override
    public void destroy() {}
}


