package com.springgoals.service;

import com.springgoals.exception.ValidationsException;
import com.springgoals.model.Faculty;

import java.sql.SQLException;
import java.util.List;

public interface FacultyService {

    List<Faculty> getAll() throws SQLException;

    Faculty getById(Integer id) throws SQLException;

    void update(Faculty faculty) throws SQLException, ValidationsException;

    void save (Faculty faculty) throws SQLException, ValidationsException;

    void delete(Integer id) throws SQLException;
}
