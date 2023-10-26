package com.springgoals.service;

import com.springgoals.exception.ValidationsException;
import com.springgoals.model.University;
import com.springgoals.model.dto.UniversityFacultiesDTO;
import com.springgoals.model.dto.UniversityFacultyDTO;


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

    UniversityFacultyDTO getFacultiesByUniId(Integer id) throws SQLException;

    void saveUniversityFaculties(UniversityFacultiesDTO updateUniversityFacultiesDTO) throws ValidationsException, SQLException;


    void deleteImages() throws SQLException;
}
