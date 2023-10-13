package com.springgoals.controller;

import com.springgoals.model.User;
import com.springgoals.model.dto.UserDTO;
import com.springgoals.service.impl.UserServiceImpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import com.springgoals.exception.AuthenticationException;
import com.springgoals.exception.EntityNotFoundException;
import com.springgoals.exception.QueryException;
import com.springgoals.exception.ValidationsException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import io.jsonwebtoken.Claims;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService ;

    ObjectMapper objectMapper = new ObjectMapper();

    @PreAuthorize("hasAuthority('SUPERUSER')")
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getUsers() throws SQLException {
        List<User> users = userService.getAll();

        return ResponseEntity.status(HttpStatus.OK).body( users );
    }
    @PreAuthorize("hasAuthority('SUPERUSER')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getById(@PathVariable("id") Integer id)  throws SQLException{

        User user = userService.getById(id);
        if (user.getId() == null) {
            throw new EntityNotFoundException("User with id " + id + " not found in DB ");
        }
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
    @PreAuthorize("hasAuthority('SUPERUSER')")
    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> update(@RequestBody User user) throws SQLException, ValidationsException  {
        if (user == null) {
            throw new ValidationsException("Error in user update: Missing user payload");
        }
        userService.update(user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully updated");
    }
    @PreAuthorize("hasAuthority('SUPERUSER')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteUser(@PathVariable("id") Integer id) throws SQLException {

        userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully updated");
    }
    @PreAuthorize("hasAuthority('SUPERUSER')")
    @RequestMapping(value = "/roles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> userRoles(
            @RequestParam("email") String email
    ) throws SQLException, QueryException {
        UserDTO userDTO;
        if (email == null || email.equals("")) {
            throw new QueryException("Error in userRoles: no email query parameter");
        } else {
            userDTO = userService.getUserRolePermissionsByEmail(email);
        }
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }
    @PreAuthorize("hasAuthority('SUPERUSER')")
    @RequestMapping(value = "/permissions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> userPermissions()  {

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();

        List<String> permissions = new ArrayList<>();
        for (GrantedAuthority authority : authorities) {
            permissions.add(authority.getAuthority());
        }

        return ResponseEntity.status(HttpStatus.OK).body( permissions );
    }

    @PreAuthorize("hasAuthority('SUPERUSER')")
    @RequestMapping(value = "/verify", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> verifyToken(ServletRequest servletRequest) throws JWTVerificationException, AuthenticationException {

        String jwtToken = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();

        System.out.println("jwtToken in verifyToken: " + jwtToken);
        Claims claims =  userService.isJWTnotValidOrExpired( jwtToken);

        if (claims == null ){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body( "jwtToken is expired or not valid" );
        }

        return ResponseEntity.status(HttpStatus.OK).body( jwtToken );
    }

    @PreAuthorize("hasAuthority('SUPERUSER')")
    @RequestMapping(value = "/edit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> setRole(
            @RequestParam("userId") Integer userId,
            @RequestParam("roleId") Integer roleId
    ) throws SQLException, QueryException, JsonProcessingException {
        if ( (userId == null) || (userId.equals("")) ) {
            throw new QueryException("Error in user edit: no userId query parameter");
        }
        else if ( (roleId  == null) || (roleId.equals("")) ) {
            throw new QueryException("Error in user edit: no roleId query parameter");
        }
        else {
            userService.setUserRole(userId, roleId);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(
                objectMapper.writeValueAsString("Successfully set user role and permissions"));
    }

}
