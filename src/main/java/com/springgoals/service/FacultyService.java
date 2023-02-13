package com.springgoals.service;

import com.springgoals.exception.ValidationsException;
import com.springgoals.model.Faculty;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface FacultyService {

    List<Faculty> getAll() throws SQLException;

    List<Faculty> searchFaculties(String name, String location, String study_field)
            throws SQLException;

    Faculty getById(Integer id) throws SQLException;

    void update(Faculty faculty) throws SQLException, ValidationsException;

    void save (Faculty faculty) throws SQLException, ValidationsException;

    void delete(Integer id) throws SQLException;
}
