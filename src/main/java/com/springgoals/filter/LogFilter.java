package com.springgoals.filter;

import javax.servlet.*;
import javax.servlet.Filter;
import java.io.IOException;

public class LogFilter implements Filter {
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
