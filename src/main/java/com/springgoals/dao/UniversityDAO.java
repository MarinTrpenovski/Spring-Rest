package com.springgoals.dao;

import com.springgoals.model.University;

import java.sql.SQLException;
import java.util.List;

public interface UniversityDAO {

    University getById(Integer id) throws SQLException;

    List<University> getAll() throws SQLException;

    List<University> searchUniversities(String sql)
            throws SQLException;

    void update(University university) throws SQLException;

    void save(University university) throws SQLException;

    void delete(Integer id) throws SQLException;

}
