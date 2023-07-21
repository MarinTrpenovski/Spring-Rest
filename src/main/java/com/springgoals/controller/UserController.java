package com.springgoals.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springgoals.exception.EmailExistsException;
import com.springgoals.exception.QueryException;
import com.springgoals.exception.EntityNotFoundException;
import com.springgoals.exception.ValidationsException;
import com.springgoals.model.University;
import com.springgoals.model.User;
import com.springgoals.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;

import java.util.ArrayList;
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
            throw new EntityNotFoundException("Уser with id " + id + " not found in DB ");
        }
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> add(@RequestBody User user)
            throws SQLException, ValidationsException,
            JsonProcessingException, EmailExistsException {
        if (user == null) {
            throw new ValidationsException("Missing user payload");}
        userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).
                body(objectMapper.writeValueAsString("Successfully Created new User"));
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> update(@RequestBody User user)
            throws SQLException, ValidationsException  {
        if (user == null) {
            throw new ValidationsException("Missing user payload");}
        userService.update(user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully updated");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteCountry(@PathVariable("id") Integer id)
            throws SQLException{

        userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully deleted");
    }
    @RequestMapping(value = "/check", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> checkUser(
            @RequestParam("email") String email
    ) throws SQLException, QueryException {
        String checkUsersMessage ;

        if (email == null || email.equals("")) {
            throw new QueryException("Error occurred: no email parameter present");
        } else {
            if(userService.checkUsers(email)){
                
          checkUsersMessage = "user with provided email already existing";
            }
            else{
                checkUsersMessage = "this email is available";

            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(checkUsersMessage);
    }
}
