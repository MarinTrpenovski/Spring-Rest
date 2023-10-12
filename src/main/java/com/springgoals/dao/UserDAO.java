package com.springgoals.dao;

import com.springgoals.model.User;
import com.springgoals.model.dto.UserDTO;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO {

    List<User> getAll() throws SQLException;

    User getById(Integer id) throws SQLException;

    void update (User user) throws SQLException;

    void delete (Integer id) throws SQLException;

    void save (User user) throws SQLException;

    boolean checkUsers(String sql) throws SQLException;

    User loginUser(String sql) throws SQLException;

    UserDTO getUserRolePermissionsByEmail(String email) throws SQLException;

    void setUserRole(Integer userId, Integer roleId) throws SQLException;
}
