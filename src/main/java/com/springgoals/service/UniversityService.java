package com.springgoals.service;

import com.springgoals.exception.ValidationsException;
import com.springgoals.model.University;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface UniversityService {

    University getById(Integer id) throws SQLException;

    List<University> getAll() throws SQLException;

    Map<Integer, University> getMap() throws SQLException;

    List<University> searchUniversities(String name, String description)
            throws SQLException;

    void update(University university) throws SQLException, ValidationsException;

    void save(University university) throws SQLException, ValidationsException;

    void delete(Integer id) throws SQLException;


}
