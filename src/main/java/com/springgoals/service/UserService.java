package com.springgoals.service;

import com.springgoals.model.User;

import java.util.List;

public interface UserService {

    List<User> getAll();

    User getById(Integer id);

    void update(User user);

    void save (User user);

    void delete(Integer id);
}
