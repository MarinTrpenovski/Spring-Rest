package com.springgoals.service;

import com.springgoals.exception.ValidationsException;
import com.springgoals.model.University;

import java.sql.SQLException;
import java.util.List;

public interface UniversityService {

    University getById(Integer id) throws SQLException;

    List<University> getAll() throws SQLException;

    void update(University university) throws SQLException, ValidationsException;

    void save(University university) throws SQLException, ValidationsException;

    void delete(Integer id ) throws SQLException;

}
