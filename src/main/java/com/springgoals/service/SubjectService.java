package com.springgoals.service;

import com.springgoals.exception.ValidationsException;

import com.springgoals.model.Subject;

import java.sql.SQLException;
import java.util.List;

public interface SubjectService {

    Subject getById(Integer id) throws SQLException;

    List<Subject> getAll() throws SQLException;

    List<Subject> searchSubjects(String name, String semester, Integer credits)
            throws SQLException;

    void update(Subject subject) throws SQLException, ValidationsException;

    void save(Subject subject) throws SQLException, ValidationsException;

    void delete(Integer id ) throws SQLException;
}
