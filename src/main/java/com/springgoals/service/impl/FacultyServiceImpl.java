package com.springgoals.service.impl;

import com.springgoals.dao.impl.FacultyDAOImpl;
import com.springgoals.exception.ValidationsException;
import com.springgoals.model.Faculty;
import com.springgoals.service.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

@Service
public class FacultyServiceImpl implements FacultyService {

    private Validator validator;

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
    public void update(Faculty faculty) throws SQLException, ValidationsException {
        Set<ConstraintViolation<Faculty>> violations = validator.validate(faculty);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<Faculty> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }

            throw new ValidationsException("Error occurred: " + sb.toString());
        }
        facultyDAO.update(faculty);
    }


    @Override
    public void save(Faculty faculty) throws SQLException, ValidationsException {
        Set<ConstraintViolation<Faculty>> violations = validator.validate(faculty);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<Faculty> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }

            throw new ValidationsException("Error occurred: " + sb.toString());
        }
        facultyDAO.save(faculty);
    }


    @Override
    public void delete(Integer id) throws SQLException {
        facultyDAO.delete(id);
    }

}
