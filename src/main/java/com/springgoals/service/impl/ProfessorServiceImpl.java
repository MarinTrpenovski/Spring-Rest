package com.springgoals.service.impl;

import com.springgoals.dao.impl.ProfessorDAOImpl;
import com.springgoals.model.Professor;
import com.springgoals.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;


@Service
public class ProfessorServiceImpl implements ProfessorService {

    @Autowired
    private ProfessorDAOImpl professorDAO;

    @Override
    public List<Professor> getAll() throws SQLException {
        return professorDAO.getAll();
    }

    @Override
    public Professor getById(Integer id) throws SQLException {
        return professorDAO.getById(id);
    }

    @Override
    public void update(Professor user) throws SQLException {
        professorDAO.update(user);
    }

    @Override
    public void save(Professor user) throws SQLException {
        professorDAO.save(user);
    }

    @Override
    public void delete(Integer id) throws SQLException {
        professorDAO.delete(id);
    }
}
