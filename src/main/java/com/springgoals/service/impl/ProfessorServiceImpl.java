package com.springgoals.service.impl;

import com.springgoals.dao.impl.ProfessorDAOImpl;
import com.springgoals.exception.CustomException;
import com.springgoals.model.Professor;
import com.springgoals.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

@Service
public class ProfessorServiceImpl implements ProfessorService {

    private Validator validator;

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
    public void update(Professor professor) throws SQLException, CustomException {
        Set<ConstraintViolation<Professor>> violations = validator.validate(professor);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<Professor> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }

            throw new CustomException("Error occurred: " + sb.toString());
        }
        professorDAO.update(professor);
    }

    @Override
    public void save(Professor professor) throws SQLException, CustomException {
        Set<ConstraintViolation<Professor>> violations = validator.validate(professor);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<Professor> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }

            throw new CustomException("Error occurred: " + sb.toString());
        }
        professorDAO.save(professor);
    }

    @Override
    public void delete(Integer id) throws SQLException {
        professorDAO.delete(id);
    }
}
