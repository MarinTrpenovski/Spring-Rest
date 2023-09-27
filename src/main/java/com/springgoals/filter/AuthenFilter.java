package com.springgoals.filter;

import com.springgoals.exception.AuthenticationException;
import com.springgoals.model.Permission;
import com.springgoals.model.Role;
import com.springgoals.model.dto.UserDTO;
import com.springgoals.security.JwtTokenUtility;
import com.springgoals.service.impl.UserServiceImpl;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenFilter implements javax.servlet.Filter {

    @Autowired
    private UserServiceImpl userService = new UserServiceImpl();

    @Autowired
    private JwtTokenUtility jwtTokenUtilitator = new JwtTokenUtility();

    @Override
    public void init(FilterConfig filterconfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "*");

        if(httpServletRequest.getMethod().equals("OPTIONS")) {
            chain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

         String jwtToken = httpServletRequest.getHeader("Authorization");


            System.out.println("jwtTokenFromHeader in AuthenFilter: " + jwtToken);

        if (jwtToken == null) {
            System.out.println("jwt token from header is missing");
            httpServletResponse.getWriter().write("jwt token from header is missing");
            return;
        }

        if (jwtToken.startsWith("Bearer ")) {
            jwtToken = jwtToken.substring(7);
        }

        System.out.println("jwtToken in AuthenFilter: " + jwtToken);
        Claims claims = null;
        try {
            claims = userService.isJWTnotValidOrExpired( jwtToken);
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
        if (claims == null) {
            System.out.println("jwt token is not valid or expired");
            httpServletResponse.getWriter().write("jwt token is not valid or expired");
            return;
        }

        //String[] jwtClaims = jwtTokenUtilitator.getSubject(jwtToken).split(",");

        String emailFromToken = claims.getIssuer();

        if (emailFromToken != null) {

            UserServiceImpl use = new UserServiceImpl();

            UserDTO userDTO;
            try {
                userDTO = use.getUserRolePermissionsByEmail(emailFromToken);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            System.out.println("UserDTO email is : " + userDTO.getEmail());

            List<GrantedAuthority> authorities = new ArrayList<>();

            for(Role role : userDTO.getRoles()) {
                for(Permission permission : role.getPermissions()) {
                    authorities.add(new SimpleGrantedAuthority(permission.getName()));
                }
            }
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(
                    new UsernamePasswordAuthenticationToken( userDTO, jwtToken, authorities ));
            System.out.println("User is authenticated.");
        }
        chain.doFilter(httpServletRequest, httpServletResponse);

    }

    @Override
    public void destroy() {}

}
