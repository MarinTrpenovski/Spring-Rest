package com.springgoals.service;

import com.springgoals.exception.EmailExistsException;
import com.springgoals.exception.ValidationsException;
import com.springgoals.model.Permission;

import java.sql.SQLException;
import java.util.List;

public interface PermissionService {

    List<Permission> getAll() throws SQLException;

    Permission getById(Integer id) throws SQLException;

    void update(Permission permission) throws SQLException, ValidationsException;

    void save (Permission permission) throws SQLException, ValidationsException, EmailExistsException;

    void delete(Integer id)throws SQLException;


}
