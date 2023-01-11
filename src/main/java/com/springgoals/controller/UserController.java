package com.springgoals.controller;

import com.springgoals.model.User;
import com.springgoals.service.impl.UserServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {


    private UserServiceImpl userService = new UserServiceImpl();

    private static List<User> users;

    private void init() {
        users = new ArrayList<>();
        users.add(new User(1, "Marin" , "Trpenovski"));
        users.add(new User(1, "Angjela" , "Milosavljevik"));
        users.add(new User(1, "Robert" , "Pavlov"));
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getUsers() {
        List<User> users = userService.getAll();
        init();
        return this.users;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public User getById(@PathVariable("id") Integer id) {
        return userService.getById(id);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public void add(@RequestBody User user) {
        users.add(user);
        userService.save(user);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody User country) {
        userService.update(country);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteCountry(@PathVariable("id") Integer id) {
        for(User user : users) {
            if(user.getId() == id) {
                users.remove(user);
            }
        }
        userService.delete(id);
    }

}
