package com.springgoals.service.impl;

import com.springgoals.dao.impl.FacultyDAOImpl;
import com.springgoals.exception.ValidationsException;
import com.springgoals.model.Faculty;
import com.springgoals.model.dto.FacultySubjectDTO;
import com.springgoals.service.FacultyService;
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
public class FacultyServiceImpl implements FacultyService {

    private static final Logger logger = LogManager.getLogger(FacultyServiceImpl.class);
    @Autowired
    private Validator validator;

    @Autowired
    private FacultyDAOImpl facultyDAO;


    @Override
    public List<Faculty> getAll() throws SQLException {
        return facultyDAO.getAll();
    }

    @Override
    public Map<Integer, Faculty> getMap() throws SQLException {
        return facultyDAO.getMap();
    }

    @Override
    public List<Faculty> searchFaculties(String name, String location, String study_field) throws SQLException {
        StringBuilder sql = new StringBuilder("Select * from faculty where 1=1");
        if (name != null && !name.equals("")) {
            sql.append(" and name = \"");
            sql.append(name);
            sql.append("\"");
        }
        if (location != null && !location.equals("")) {
            sql.append(" and location = \"");
            sql.append(location);
            sql.append("\"");
        }
        if (study_field != null && !study_field.equals("")) {
            sql.append(" and study_field = \"");
            sql.append(study_field);
            sql.append("\"");
        }

        return facultyDAO.searchFaculties(sql.toString());

    }

    @Override
    public Faculty getById(Integer id) throws SQLException {
        return facultyDAO.getById(id);
    }

    @Transactional
    @Override
    public void update(Faculty faculty) throws SQLException, ValidationsException {
        Set<ConstraintViolation<Faculty>> violations = this.validator.validate(faculty);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<Faculty> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }
            logger.error("Error in FacultyServiceImpl update: " + sb.toString());
            throw new ValidationsException("Error in FacultyServiceImpl update: " + sb.toString());
        }
        facultyDAO.update(faculty);
    }

    @Override
    @Transactional
    public void save(Faculty faculty) throws SQLException, ValidationsException {
        Set<ConstraintViolation<Faculty>> violations = validator.validate(faculty);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<Faculty> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }
            logger.error("Error in FacultyServiceImpl save: " + sb.toString());
            throw new ValidationsException("Error in FacultyServiceImpl save: " + sb.toString());
        }
        facultyDAO.save(faculty);
    }

    @Override
    @Transactional
    public void delete(Integer id) throws SQLException {
        facultyDAO.delete(id);
    }

    @Override
    public FacultySubjectDTO getSubjectsByFacId(Integer id) throws SQLException {

        return facultyDAO.getSubjectsByFacId(id);

    }

    @Override
    public void deleteImages() throws SQLException {
        this.facultyDAO.deleteImages();
    }
}
