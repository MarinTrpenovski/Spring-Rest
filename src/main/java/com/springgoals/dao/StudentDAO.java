package com.springgoals.dao;

import com.springgoals.model.Student;

import java.sql.SQLException;
import java.util.List;


public interface StudentDAO {

    Student getById(Integer id) throws SQLException;

    List<Student> getAll() throws SQLException;

    void update(Student student) throws SQLException;

    void save(Student student) throws SQLException;

    void delete(Integer id) throws SQLException;


}
