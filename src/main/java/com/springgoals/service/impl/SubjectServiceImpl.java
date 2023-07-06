package com.springgoals.service.impl;

import com.springgoals.dao.impl.SubjectDAOImpl;
import com.springgoals.exception.ValidationsException;
import com.springgoals.model.Subject;
import com.springgoals.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

@Service

public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private Validator validator;

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
    public Map<Integer, Subject> getMap() throws SQLException {
        return subjectDAO.getMap();
    }

    @Override
    public List<Subject> searchSubjects(String name, String semester, Integer credits)
            throws SQLException {
        StringBuilder sql = new StringBuilder("Select * from subject where 1=1");
        if (name != null && !name.equals("")) {
            sql.append(" and name = \"");
            sql.append(name);
            sql.append("\"");
        }
        if (semester != null && !semester.equals("")) {
            sql.append(" and semester = \"");
            sql.append(semester);
            sql.append("\"");
        }
        if (credits != null && credits != 0) {
            sql.append(" and credits = ");
            sql.append(credits);
        }

        return subjectDAO.searchSubjects(sql.toString());

    }

    @Override
    @Transactional
    public void update(Subject subject) throws SQLException, ValidationsException {
        Set<ConstraintViolation<Subject>> violations = validator.validate(subject);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<Subject> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }

            throw new ValidationsException("Error occurred: " + sb.toString());
        }
        subjectDAO.update(subject);
    }

    @Override
    @Transactional
    public void save(Subject subject) throws SQLException, ValidationsException {
        Set<ConstraintViolation<Subject>> violations = validator.validate(subject);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<Subject> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }

            throw new ValidationsException("Error occurred: " + sb.toString());
        }
        subjectDAO.save(subject);
    }

    @Override
    @Transactional
    public void delete(Integer id) throws SQLException {
        subjectDAO.delete(id);
    }
}
