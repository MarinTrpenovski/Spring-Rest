package com.springgoals.service;

import com.springgoals.exception.AuthenticationException;
import com.springgoals.exception.EmailExistsException;
import com.springgoals.exception.ValidationsException;
import com.springgoals.model.User;
import com.springgoals.model.dto.UserDTO;
import io.jsonwebtoken.Claims;

import java.sql.SQLException;
import java.util.List;

public interface UserService {

    List<User> getAll() throws SQLException;

    User getById(Integer id) throws SQLException;

    void update(User user) throws SQLException, ValidationsException;

    String save (User user) throws SQLException, ValidationsException, EmailExistsException;

    void delete(Integer id)throws SQLException;

    boolean checkUsers(String email) throws SQLException;

    Claims isJWTnotValidOrExpired(String jwtToken) throws AuthenticationException;

    String loginUser(String email, String password) throws SQLException, AuthenticationException;

    UserDTO getUserRolePermissionsByEmail(String email) throws SQLException;
}
