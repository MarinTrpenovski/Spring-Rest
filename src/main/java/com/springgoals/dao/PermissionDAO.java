package com.springgoals.dao;

import com.springgoals.model.Permission;

import java.sql.SQLException;
import java.util.List;

public interface PermissionDAO {

    List<Permission> getAll() throws SQLException;

    Permission getById(Integer id) throws SQLException;

    void update (Permission permission) throws SQLException;

    void delete (Integer id) throws SQLException;

    void save (Permission permission) throws SQLException;
}
