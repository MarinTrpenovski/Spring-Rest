package com.springgoals.service;

import com.springgoals.model.Professor;

import java.sql.SQLException;
import java.util.List;

public interface ProfessorService {

    Professor getById(Integer id) throws SQLException;

    List<Professor> getAll() throws SQLException;

    void update(Professor professor) throws SQLException;

    void save(Professor professor) throws SQLException;

    void delete(Integer id ) throws SQLException;
}
