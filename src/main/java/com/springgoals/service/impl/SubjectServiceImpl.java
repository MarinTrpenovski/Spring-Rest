package com.springgoals.service.impl;

import com.springgoals.dao.impl.FacultyDAOImpl;
import com.springgoals.dao.impl.SubjectDAOImpl;
import com.springgoals.model.Subject;
import com.springgoals.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;


@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectDAOImpl subjectDAO;


    @Override
    public Subject getById(Integer id) throws SQLException {
        Subject subject = subjectDAO.getById(id);
        return subject;
    }

    @Override
    public List<Subject> getAll() throws SQLException {
        return subjectDAO.getAll();
    }

    @Override
    public void update(Subject subject) throws SQLException {
        subjectDAO.update(subject);
    }

    @Override
    public void save(Subject subject) throws SQLException {
        subjectDAO.save(subject);
    }

    @Override
    public void delete(Integer id) throws SQLException {
        subjectDAO.delete(id);
    }
}
