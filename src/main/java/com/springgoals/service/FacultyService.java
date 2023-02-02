package com.springgoals.service;

import com.springgoals.model.Faculty;

import java.sql.SQLException;
import java.util.List;

public interface FacultyService {

    List<Faculty> getAll() throws SQLException;

    Faculty getById(Integer id) throws SQLException;

    void update(Faculty user) throws SQLException;

    void save (Faculty user) throws SQLException;

    void delete(Integer id) throws SQLException;
}
