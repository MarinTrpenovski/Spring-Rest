package com.springgoals.service;

import com.springgoals.exception.CustomException;
import com.springgoals.model.Faculty;

import java.sql.SQLException;
import java.util.List;

public interface FacultyService {

    List<Faculty> getAll() throws SQLException;

    Faculty getById(Integer id) throws SQLException;

    void update(Faculty faculty) throws SQLException, CustomException;

    void save (Faculty faculty) throws SQLException, CustomException;

    void delete(Integer id) throws SQLException;
}
