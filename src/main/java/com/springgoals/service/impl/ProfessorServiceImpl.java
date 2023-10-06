package com.springgoals.service.impl;

import com.springgoals.controller.LogController;
import com.springgoals.dao.impl.ProfessorDAOImpl;
import com.springgoals.exception.ValidationsException;
import com.springgoals.model.Professor;
import com.springgoals.service.ProfessorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

public class ProfessorServiceImpl implements ProfessorService {

    private static final Logger logger = LogManager.getLogger(ProfessorServiceImpl.class);

    @Autowired
    private Validator validator;

    @Autowired
    private ProfessorDAOImpl professorDAO;

    @Override
    public List<Professor> getAll() throws SQLException {
        return professorDAO.getAll();
    }

    @Override
    public Map<Integer, Professor> getMap() throws SQLException {
        return professorDAO.getMap();
    }

    @Override
    public List<Professor> searchProfessors(
            String name, String surname, Integer age, String primary_subject1, String primary_subject2)
            throws SQLException {
        StringBuilder sql = new StringBuilder("Select * from professor where 1=1");
        if (name != null && !name.equals("")) {
            sql.append(" and name = \"");
            sql.append(name);
            sql.append("\"");
        }
        if (surname != null && !surname.equals("")) {
            sql.append(" and surname = \"");
            sql.append(surname);
            sql.append("\"");
        }
        if (age != null && age != 0) {
            sql.append(" and age = ");
            sql.append(age);
        }
        if (primary_subject1 != null && !primary_subject1.equals("")) {
            sql.append(" and primary_subject1 = \"");
            sql.append(primary_subject1);
            sql.append("\"");
        }
        if (primary_subject2 != null && !primary_subject2.equals("")) {
            sql.append(" and primary_subject2 = \"");
            sql.append(primary_subject2);
            sql.append("\"");
        }

        return professorDAO.searchProfessors(sql.toString());

    }

    @Override
    public Professor getById(Integer id) throws SQLException {
        return professorDAO.getById(id);
    }

    @Override
    @Transactional
    public void update(Professor professor) throws SQLException, ValidationsException {
        Set<ConstraintViolation<Professor>> violations = validator.validate(professor);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<Professor> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }
            logger.error("Error in ProfessorServiceImpl update: " + sb.toString());
            throw new ValidationsException("Error in ProfessorServiceImpl update: " + sb.toString());
        }
        professorDAO.update(professor);
    }

    @Override
    @Transactional
    public void save(Professor professor) throws SQLException, ValidationsException {
        Set<ConstraintViolation<Professor>> violations = validator.validate(professor);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<Professor> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }
            logger.error("Error in ProfessorServiceImpl save: " + sb.toString());
            throw new ValidationsException("Error in ProfessorServiceImpl save: " + sb.toString());
        }
        professorDAO.save(professor);
    }

    @Override
    @Transactional
    public void delete(Integer id) throws SQLException {
        professorDAO.delete(id);
    }
}
