package com.springgoals.service;

import com.springgoals.exception.ValidationsException;
import com.springgoals.model.Student;

import java.sql.SQLException;
import java.util.List;

public interface StudentService {

    Student getById(Integer id) throws SQLException;

    List<Student> getAll() throws SQLException;

    List<Student> searchStudents(String name, String surname, String location, Integer indeks)
            throws SQLException;

    void update(Student student) throws SQLException, ValidationsException;

    void save(Student student) throws SQLException, ValidationsException;

    void delete(Integer id) throws SQLException;

}
