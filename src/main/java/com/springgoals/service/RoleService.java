package com.springgoals.service;

import com.springgoals.exception.EmailExistsException;
import com.springgoals.exception.ValidationsException;
import com.springgoals.model.Role;

import java.sql.SQLException;
import java.util.List;

public interface RoleService {

    List<Role> getAll() throws SQLException;

    Role getById(Integer id) throws SQLException;

    void update(Role role) throws SQLException, ValidationsException;

    void save (Role role) throws SQLException, ValidationsException, EmailExistsException;

    void delete(Integer id)throws SQLException;


}
