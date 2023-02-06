package com.springgoals.dao;

import com.springgoals.model.Professor;

import java.sql.SQLException;
import java.util.List;

public interface ProfessorDAO {

    Professor getById(Integer id) throws SQLException;


    List<Professor> getAll() throws SQLException;


    void save(Professor professor) throws SQLException;


    void delete(Integer id) throws SQLException;


    void update(Professor professor) throws SQLException;


}
