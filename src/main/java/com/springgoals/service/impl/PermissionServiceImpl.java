package com.springgoals.service.impl;

import com.springgoals.controller.LogController;
import com.springgoals.dao.impl.PermissionDAOImpl;
import com.springgoals.exception.ValidationsException;
import com.springgoals.model.Permission;

import com.springgoals.service.PermissionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class PermissionServiceImpl implements PermissionService {

    private static final Logger logger = LogManager.getLogger(PermissionServiceImpl.class);

    @Autowired
    private Validator validator;

    @Autowired
    private PermissionDAOImpl permissionDAO = new PermissionDAOImpl();


    @Override
    public List<Permission> getAll() throws SQLException {
        return permissionDAO.getAll();
    }

    @Override
    public Permission getById(Integer id) throws SQLException {
        Permission permission = permissionDAO.getById(id);
        return permission;
    }

    @Override
    @Transactional
    public void update(Permission permission) throws SQLException, ValidationsException {
        Set<ConstraintViolation<Permission>> violations = validator.validate(permission);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<Permission> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }
            logger.error("Error in PermissionServiceImpl update: " + sb.toString());
            throw new ValidationsException("Error in PermissionServiceImpl update: " + sb.toString());
        }
        permissionDAO.update(permission);
    }

    @Override
    @Transactional
    public void save(Permission permission) throws SQLException, ValidationsException {
        Set<ConstraintViolation<Permission>> violations = validator.validate(permission);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<Permission> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }
            logger.error("Error in PermissionServiceImpl save: " + sb.toString());
            throw new ValidationsException("Error in PermissionServiceImpl save: " + sb.toString());
        }
        permissionDAO.save(permission);
    }


    @Override
    public void delete(Integer id) throws SQLException {
        permissionDAO.delete(id);
    }


}
