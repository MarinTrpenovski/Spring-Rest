package com.springgoals.dao;

import com.springgoals.model.Professor;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ProfessorDAO {

    Professor getById(Integer id) throws SQLException;

    List<Professor> getAll() throws SQLException;

    Map<Integer, Professor> getMap() throws SQLException;

    List<Professor> searchProfessors(String sql)
            throws SQLException;

    void save(Professor professor) throws SQLException;


    void delete(Integer id) throws SQLException;


    Integer saveReturnId(Professor professor) throws SQLException;

    void update(Professor professor) throws SQLException;


    void deleteImages() throws SQLException;
}
