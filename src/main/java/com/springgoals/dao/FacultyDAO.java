package com.springgoals.dao;

import com.springgoals.model.Faculty;

import java.sql.SQLException;
import java.util.List;

public interface FacultyDAO {

    List<Faculty> getAll() throws SQLException;

    Faculty getById(Integer id) throws SQLException;

    void update (Faculty user) throws SQLException;

    void delete (Integer id) throws SQLException;

    void save (Faculty user) throws SQLException;
	
}
