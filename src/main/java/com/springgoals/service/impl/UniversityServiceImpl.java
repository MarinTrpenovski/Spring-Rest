package com.springgoals.service.impl;

import com.springgoals.dao.impl.UniversityDAOImpl;
import com.springgoals.exception.ValidationsException;
import com.springgoals.model.University;
import com.springgoals.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

@Service
public class UniversityServiceImpl implements UniversityService {

    private Validator validator;

    @Autowired
    private UniversityDAOImpl universityDAO;

    @Override
    public University getById(Integer id) throws SQLException {
        University university = universityDAO.getById(id);
        return university;
    }

    @Override
    public List<University> getAll() throws SQLException {
        return universityDAO.getAll();
    }

    @Override
    public void update(University university) throws SQLException, ValidationsException {
        Set<ConstraintViolation<University>> violations = validator.validate(university);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<University> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }

            throw new ValidationsException("Error occurred: " + sb.toString());
        }
        universityDAO.update(university);
    }

    @Override
    public void save(University university) throws SQLException, ValidationsException {
        Set<ConstraintViolation<University>> violations = validator.validate(university);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<University> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }

            throw new ValidationsException("Error occurred: " + sb.toString());
        }
        universityDAO.save(university);
    }

    @Override
    public void delete(Integer id) throws SQLException {

        universityDAO.delete(id);

    }
}
