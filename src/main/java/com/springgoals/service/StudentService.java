package com.springgoals.service;

import com.springgoals.exception.CustomException;
import com.springgoals.model.Student;

import java.sql.SQLException;
import java.util.List;

public interface StudentService {

    Student getById(Integer id) throws SQLException;

    List<Student> getAll() throws SQLException;

    void update(Student student) throws SQLException, CustomException;

    void save(Student student) throws SQLException, CustomException;

    void delete(Integer id ) throws SQLException;

}
