package com.springgoals.service.impl;

import com.springgoals.dao.impl.FacultyDAOImpl;
import com.springgoals.model.Faculty;
import com.springgoals.service.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class FacultyServiceImpl implements FacultyService {

    @Autowired
    private FacultyDAOImpl facultyDAO;


    @Override
    public List<Faculty> getAll() throws SQLException {
        return facultyDAO.getAll();
    }

    @Override
    public Faculty getById(Integer id) throws SQLException {
        return facultyDAO.getById(id);
    }

    @Override
    public void update(Faculty user) throws SQLException {
        facultyDAO.update(user);
    }

    @Override
    public void save(Faculty user) throws SQLException {
        facultyDAO.save(user);
    }

    @Override
    public void delete(Integer id) throws SQLException {
        facultyDAO.delete(id);
    }
}
