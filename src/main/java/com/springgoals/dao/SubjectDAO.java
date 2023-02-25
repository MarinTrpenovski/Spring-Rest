package com.springgoals.dao;

import com.springgoals.model.Subject;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface SubjectDAO {

    Subject getById(Integer id) throws SQLException;

    List<Subject> getAll() throws SQLException;

    Map<Integer, Subject> getMap() throws SQLException;

    List<Subject> searchSubjects(String sql)
            throws SQLException;

    void update(Subject subject) throws SQLException;

    void save(Subject subject) throws SQLException;

    Integer saveReturnId(Subject subject) throws SQLException;

    void delete(Integer id) throws SQLException;

    Integer deleteDTO(Integer studentId, Integer subjectId) throws SQLException;
}
