package com.springgoals.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springgoals.exception.EntityNotFoundException;
import com.springgoals.exception.QueryException;
import com.springgoals.exception.ValidationsException;
import com.springgoals.model.User;
import com.springgoals.model.dto.UserDTO;
import com.springgoals.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Collection;
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

    @RequestMapping(value = "/permissions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GrantedAuthority>> userPermissions()  {

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();

        return ResponseEntity.status(HttpStatus.OK).body( authorities );
    }

}
