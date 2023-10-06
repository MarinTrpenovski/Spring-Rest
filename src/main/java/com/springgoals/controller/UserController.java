package com.springgoals.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springgoals.exception.AuthenticationException;
import com.springgoals.exception.EntityNotFoundException;
import com.springgoals.exception.QueryException;
import com.springgoals.exception.ValidationsException;
import com.springgoals.model.User;
import com.springgoals.model.dto.UserDTO;
import com.springgoals.service.impl.UserServiceImpl;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService ;

    ObjectMapper objectMapper = new ObjectMapper();

    @Secured("Admin")
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getUsers() throws SQLException {
        List<User> users = userService.getAll();

        return ResponseEntity.status(HttpStatus.OK).body( users );
    }
    @Secured("Admin")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getById(@PathVariable("id") Integer id)  throws SQLException{

        User user = userService.getById(id);
        if (user.getId() == null) {
            throw new EntityNotFoundException("User with id " + id + " not found in DB ");
        }
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
    @Secured("Admin")
    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> update(@RequestBody User user) throws SQLException, ValidationsException  {
        if (user == null) {
            throw new ValidationsException("Missing user payload");
        }
        userService.update(user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully updated");
    }
    @Secured("Admin")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteUser(@PathVariable("id") Integer id) throws SQLException {

        userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully deleted");
    }
    @Secured("Admin")
    @RequestMapping(value = "/roles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> userRoles(
            @RequestParam("email") String email
    ) throws SQLException, QueryException {
        UserDTO userDTO;
        if (email == null || email.equals("")) {
            throw new QueryException("Error occurred: not enough query parameters");
        } else {
            userDTO = userService.getUserRolePermissionsByEmail(email);
        }
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }
    @Secured("Admin")
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

}
