package com.springgoals.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springgoals.exception.AuthenticationException;
import com.springgoals.exception.EmailExistsException;
import com.springgoals.exception.QueryException;
import com.springgoals.exception.ValidationsException;
import com.springgoals.model.User;
import com.springgoals.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserServiceImpl userService ;

    ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> add(@RequestBody User user) throws SQLException, ValidationsException, JsonProcessingException, EmailExistsException {
        if (user == null) {
            throw new ValidationsException("Missing user payload");
        }
        String signupUserToken = userService.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).
                body(objectMapper.writeValueAsString( signupUserToken ));
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> loginUser(
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ) throws SQLException, QueryException, JsonProcessingException, AuthenticationException {

        if ( (email == null || email.equals("")) || (password == null || password.equals("")) ){
            throw new QueryException("Error occurred: no email or password parameter present");
        }
        String loginUserToken =  userService.loginUser( email, password ) ;

        System.out.println("login from controller: " + loginUserToken);

        return ResponseEntity.status(HttpStatus.OK).
                body( objectMapper.writeValueAsString( loginUserToken ) );
    }

    @RequestMapping(value = "/verify", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> verifyToken(ServletRequest servletRequest) throws JWTVerificationException, AuthenticationException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        String jwtToken = httpServletRequest.getHeader("Authorization")  ;

        System.out.println( "jwt token in verifyToken = " + jwtToken );

        if (jwtToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body( "jwtToken is missing" );
        }

        if ( userService.isJWTnotValidOrExpired( jwtToken) ){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body( "jwtToken is expired or not valid" );
        }

        return ResponseEntity.status(HttpStatus.OK).body( jwtToken );
    }
}
