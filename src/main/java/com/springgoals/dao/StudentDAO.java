package com.springgoals.dao;

import com.springgoals.model.Student;
import com.springgoals.model.dto.StudentDTO;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;


public interface StudentDAO {

    Student getById(Integer id) throws SQLException;

    List<Student> getAll() throws SQLException;

    Map<Integer, Student> getMap() throws SQLException;

    List<Student> searchStudents(String sql)
            throws SQLException;

    void update(Student student) throws SQLException;

    void save(Student student) throws SQLException;

    void delete(Integer id) throws SQLException;

    StudentDTO getSubjectsByStudId(Integer id) throws SQLException;
}
