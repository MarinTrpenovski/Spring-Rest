package com.springgoals.service;

import com.springgoals.exception.ValidationsException;
import com.springgoals.model.Professor;

import java.sql.SQLException;
import java.util.List;

public interface ProfessorService {

    Professor getById(Integer id) throws SQLException;

    List<Professor> getAll() throws SQLException;

    List<Professor> searchProfessors(String name, String surname, Integer age, String primary_subject1, String primary_subject2)
            throws SQLException;

    void update(Professor professor) throws SQLException, ValidationsException;

    void save(Professor professor) throws SQLException, ValidationsException;

    void delete(Integer id) throws SQLException;
}
