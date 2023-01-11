package com.springgoals.service.impl;

import com.springgoals.dao.impl.UserDAOImpl;
import com.springgoals.model.User;
import com.springgoals.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDAOImpl userDAO = new UserDAOImpl();

    @Override
    public List<User> getAll() {
        return userDAO.getAll();
    }

    @Override
    public User getById(Integer id) {
        return userDAO.getById(id);
    }

    @Override
    public void update(User user) {
        userDAO.update(user);
    }

    @Override
    public void save(User user) {
        userDAO.save(user);
    }

    @Override
    public void delete(Integer id) {
        userDAO.delete(id);
    }
}
