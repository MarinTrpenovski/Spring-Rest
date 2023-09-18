package com.springgoals.dao;

import com.springgoals.model.Role;

import java.sql.SQLException;
import java.util.List;

public interface RoleDAO {

    List<Role> getAll() throws SQLException;

    Role getById(Integer id) throws SQLException;

    void update (Role role) throws SQLException;

    void delete (Integer id) throws SQLException;

    void save (Role role) throws SQLException;
}
