package com.springgoals.service.impl;

import com.springgoals.controller.LogController;
import com.springgoals.dao.impl.RoleDAOImpl;
import com.springgoals.exception.ValidationsException;
import com.springgoals.model.Role;
import com.springgoals.service.RoleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class RoleServiceImpl implements RoleService {

    private static final Logger logger = LogManager.getLogger(RoleServiceImpl.class);
    @Autowired
    private Validator validator;

    @Autowired
    private RoleDAOImpl roleDAO = new RoleDAOImpl();


    @Override
    public List<Role> getAll() throws SQLException {
        return roleDAO.getAll();
    }

    @Override
    public Role getById(Integer id) throws SQLException {
        Role role = roleDAO.getById(id);
        return role;
    }

    @Override
    @Transactional
    public void update(Role role) throws SQLException, ValidationsException {
        Set<ConstraintViolation<Role>> violations = validator.validate(role);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<Role> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }
            logger.error("Error in RoleServiceImpl update: " + sb.toString());
            throw new ValidationsException("Error in RoleServiceImpl update: " + sb.toString());
        }
        roleDAO.update(role);
    }

    @Override
    @Transactional
    public void save(Role role) throws SQLException, ValidationsException {
        Set<ConstraintViolation<Role>> violations = validator.validate(role);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<Role> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }
            logger.error("Error in RoleServiceImpl save: " + sb.toString());
            throw new ValidationsException("Error in RoleServiceImpl save: " + sb.toString());
        }
        roleDAO.save(role);
    }


    @Override
    public void delete(Integer id) throws SQLException {
        roleDAO.delete(id);
    }



}
