package com.springgoals.dao;

import com.springgoals.model.User;

import java.util.List;

public interface UserDAO {

    List<User> getAll();

    User getById(Integer id);

    void update (User user);

    void delete (Integer id);

    void save (User user);
}
