package com.springgoals.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springgoals.exception.*;
import com.springgoals.model.User;
import com.springgoals.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService ;

    ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getUsers() throws SQLException {
        List<User> users = userService.getAll();

        return ResponseEntity.status(HttpStatus.OK).body( users );
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getById(@PathVariable("id") Integer id)  throws SQLException{
        User user = userService.getById(id);
        if (user.getId() == null) {
            throw new EntityNotFoundException("User with id " + id + " not found in DB ");
        }
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> add(@RequestBody User user) throws SQLException, ValidationsException, JsonProcessingException, EmailExistsException {
        if (user == null) {
            throw new ValidationsException("Missing user payload");
        }
        userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).
                body(objectMapper.writeValueAsString("Successfully Created new User"));
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> update(@RequestBody User user) throws SQLException, ValidationsException  {
        if (user == null) {
            throw new ValidationsException("Missing user payload");
        }
        userService.update(user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully updated");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteUser(@PathVariable("id") Integer id) throws SQLException {

        userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully deleted");
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> loginUser(
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ) throws SQLException, QueryException {

        if ( (email == null || email.equals("")) || (password == null || password.equals("")) ){
            throw new QueryException("Error occurred: no email or password parameter present");
        }
        String loginUserToken =  userService.loginUser( email, password ) ;

        return ResponseEntity.status(HttpStatus.OK).body( loginUserToken );
    }

    @RequestMapping(value = "/verify", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> verifyToken(ServletRequest servletRequest) throws JWTVerificationException, JWTException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        String jwtToken = httpServletRequest.getHeader("Authorization")  ;

        if (jwtToken == null) {
            return ResponseEntity.status(HttpStatus.OK).body( "jwtToken is missing" );
        }

        if ( userService.isJWTnotValidOrExpired( jwtToken) == true ){
            throw new JWTException("Error occurred: token is not valid or expired");
        }

        return ResponseEntity.status(HttpStatus.OK).body( jwtToken );
    }
}
