package com.springgoals.dao;

import com.springgoals.model.University;
import com.springgoals.model.dto.UniversityFacultyDTO;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface UniversityDAO {

    University getById(Integer id) throws SQLException;

    List<University> getAll() throws SQLException;

    Map<Integer, University> getMap()throws SQLException ;

    List<University> searchUniversities(String sql)
            throws SQLException;

    void update(University university) throws SQLException;

    void save(University university) throws SQLException;

    Integer saveReturnId(University university) throws SQLException;

    void delete(Integer id) throws SQLException;

    UniversityFacultyDTO getFacultiesByUniId(Integer id) throws SQLException;


}
