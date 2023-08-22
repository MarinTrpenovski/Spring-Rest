package com.springgoals.service.impl;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.springgoals.dao.impl.UserDAOImpl;
import com.springgoals.exception.EmailExistsException;
import com.springgoals.exception.EntityNotFoundException;
import com.springgoals.exception.ValidationsException;
import com.springgoals.model.User;
import com.springgoals.security.JwtTokenUtility;
import com.springgoals.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private Validator validator;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDAOImpl userDAO = new UserDAOImpl();


    @Autowired
    private JwtTokenUtility jwtTokenUtilitator;

    //@Autowired
    private JWTVerifier jwtVerifier ;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public List<User> getAll() throws SQLException {
        return userDAO.getAll();
    }

    @Override
    public User getById(Integer id) throws SQLException {
        User user = userDAO.getById(id);
        return user;
    }

    @Override
    @Transactional
    public void update(User user) throws SQLException, ValidationsException {
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<User> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }

            throw new ValidationsException("Error occurred: " + sb.toString());
        }
        userDAO.update(user);
    }

    @Override
    @Transactional
    public void save(User user) throws SQLException, ValidationsException, EmailExistsException {

        if (checkUsers(user.getEmail())) {
            throw new EmailExistsException("There is already a user with the given email address");
        }

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<User> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }

            throw new ValidationsException("Error occurred: " + sb.toString());
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userDAO.save(user);
    }

    @Override
    public void delete(Integer id) throws SQLException {
        userDAO.delete(id);
    }

    @Override
    public boolean checkUsers(String email) throws SQLException {

        StringBuilder sql = new StringBuilder("Select * from user where 1=1");
        if (email != null && !email.equals("")) {
            sql.append(" and email = \"");
            sql.append(email);
            sql.append("\"");
        }

        return userDAO.checkUsers(sql.toString());
    }


    @Override
    public boolean isJWTnotValidOrExpired(String jwtToken) {
        boolean isExpiredOrNotVerified ;

        try {
            DecodedJWT decodedJWT = jwtVerifier.verify(jwtToken);
            Date expiresAt = decodedJWT.getExpiresAt();
            isExpiredOrNotVerified = expiresAt.before(new Date());
        } catch (JWTVerificationException e) {
            System.out.println( "jwt token is not valid or expired" );
            return true;
        }

        return isExpiredOrNotVerified ;
    }


    @Override
    public String loginUser(String email, String password) throws SQLException {

        String encodedPassword = passwordEncoder.encode( password );

        StringBuilder sql = new StringBuilder("Select * from user where 1=1");
        if (email != null && !email.equals("")) {
            sql.append(" and email = \"");
            sql.append(email);
            sql.append("\"");
        }

        if (encodedPassword != null && !encodedPassword.equals("")) {
            sql.append(" and password = \"");
            sql.append(password);
            sql.append("\"");
        }

        User user =  userDAO.loginUser(sql.toString());

        if (user.getId() == null) {
            throw new EntityNotFoundException("User with the provided credentials is not found in DB.");
        }

        return  jwtTokenUtilitator.generateJWTToken( user ) ;
    }
}
