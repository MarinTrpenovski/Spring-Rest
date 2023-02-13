package com.springgoals.dao;

import com.springgoals.exception.QueryException;
import com.springgoals.model.Faculty;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;


public interface FacultyDAO {

    List<Faculty> getAll() throws SQLException;

    List<Faculty> searchFaculties(String sql) throws SQLException;

    Faculty getById(Integer id) throws SQLException;

    void update(Faculty user) throws SQLException;

    void delete(Integer id) throws SQLException;

    void save(Faculty user) throws SQLException;

}
