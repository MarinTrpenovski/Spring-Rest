package com.springgoals.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springgoals.exception.AuthenticationException;
import com.springgoals.exception.EmailExistsException;
import com.springgoals.exception.QueryException;
import com.springgoals.exception.ValidationsException;
import com.springgoals.model.User;
import com.springgoals.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private static final Logger logger = LogManager.getLogger(LogController.class);
    @Autowired
    private UserServiceImpl userService ;

    ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> add(@RequestBody User user) throws SQLException, ValidationsException, JsonProcessingException, EmailExistsException {
        if (user == null) {
            logger.error("Missing user payload");
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
            logger.error("Error occurred: no email or password parameter present");
            throw new QueryException("Error occurred: no email or password parameter present");
        }
        String loginUserToken =  userService.loginUser( email, password ) ;

        System.out.println("login from controller: " + loginUserToken);

        return ResponseEntity.status(HttpStatus.OK).
                body( objectMapper.writeValueAsString( loginUserToken ) );
    }


}
