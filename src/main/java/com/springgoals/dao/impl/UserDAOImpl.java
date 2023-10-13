package com.springgoals.dao.impl;

import com.springgoals.dao.SingletonConnection;
import com.springgoals.dao.UserDAO;
import com.springgoals.model.Permission;
import com.springgoals.model.Role;
import com.springgoals.model.User;
import com.springgoals.model.dto.UserDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

@Repository
public class UserDAOImpl implements UserDAO {

    private static final Logger logger = LogManager.getLogger(UserDAOImpl.class);

    static Connection connection;

    static {
        try {
            connection = SingletonConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getById(Integer id) throws SQLException  {

        User user = new User();
        try {
            connection = SingletonConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            StringBuilder sql = new StringBuilder("select * from user where id = ");

            sql.append(id);
            ResultSet resultSet = statement.executeQuery(sql.toString());

            while (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                user.setSurname(resultSet.getString("surname"));

            }
        } catch (SQLException e) {
            System.out.println("error occurred in UserDAOImpl getById " + e.getMessage());
            logger.error("error occurred in UserDAOImpl getById " + e.getMessage());
            throw e;

        }
        return user;
    }

    @Override
    public List<User> getAll() throws SQLException  {

        List<User> userList = new ArrayList<>();
        try {

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from user");
            while (rs.next()) {
                User user = new User();
                user.setName(rs.getString("name"));
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setSurname(rs.getString("surname"));

                userList.add(user);
            }
        } catch (SQLException e) {
            System.out.println("error occurred in UserDAOImpl getAll " + e.getMessage());
            logger.error("error occurred in UserDAOImpl getAll " + e.getMessage());
            throw e;

        }
        return userList;

    }


    @Override
    public void save(User user)  throws SQLException {

        try {
            String sql = "INSERT INTO user (name, email , password , surname) VALUES (?, ? ,? ,?)";
            PreparedStatement statement1 = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement1.setString(1, user.getName());
            statement1.setString(2, user.getEmail());
            statement1.setString(3, user.getPassword());
            statement1.setString(4, user.getSurname());

            int affectedRows = statement1.executeUpdate();
            if (affectedRows == 0) {
                logger.error("Error with affectedRows in UserDAOImpl save");
                throw new SQLException("Error with affectedRows in UserDAOImpl save");
            }

        } catch (SQLException e) {
            System.out.println("error occurred in UserDAOImpl save " + e.getMessage());
            logger.error("error occurred in UserDAOImpl save " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void update(User user) throws SQLException  {

        try {
            connection = SingletonConnection.getInstance().getConnection();
            String sql = "UPDATE education.user SET name=?, email=? , password =? , surname =? WHERE id=?";
            PreparedStatement statement1 = connection.prepareStatement(sql);
            statement1.setString(1, user.getName());
            statement1.setString(2, user.getEmail());
            statement1.setString(3, user.getPassword());
            statement1.setString(4, user.getSurname());
            statement1.setInt(5, user.getId());

            int rowsUpdated = statement1.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing user was updated");
            }

        } catch (SQLException e) {
            System.out.println("error occurred in UserDAOImpl update " + e.getMessage());
            logger.error("error occurred in UserDAOImpl update " + e.getMessage());
            throw e;
        }

    }

    @Override
    public void delete(Integer id)  throws SQLException {

        try {
            String sql = "DELETE FROM user WHERE id=?";
            PreparedStatement statement1 = connection.prepareStatement(sql);
            statement1.setInt(1, id);

            int rowsUpdated = statement1.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("user with id " + id + " was deleted");
            }

        } catch (SQLException e) {
            System.out.println("error occurred in UserDAOImpl delete " + e.getMessage());
            logger.error("error occurred in UserDAOImpl delete " + e.getMessage());
            throw e;

        }

    }

    @Override
    public boolean checkUsers(String sql) throws SQLException {

        try {
            connection = SingletonConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()) {
             return  true;
            }
            else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println("error occurred in UserDAOImpl checkUsers " + e.getMessage());
            logger.error("error occurred in UserDAOImpl checkUsers " + e.getMessage());
            throw e;
        }

    }

    @Override
    public User loginUser(String sql) throws SQLException {

        User user = new User();

        try {
            connection = SingletonConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setSurname(rs.getString("surname"));
            }

        } catch (SQLException e) {
            System.out.println("error in UserDAOImpl loginUser " + "Invalid username or password" + e.getMessage());
            logger.error("error in UserDAOImpl loginUser " + "Invalid username or password" + e.getMessage());
            throw e;
        }
        return user;
    }

    @Override
    public UserDTO getUserRolePermissionsByEmail(String email) throws SQLException {

        UserDTO userDTO = new UserDTO();

        try {
            connection = SingletonConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();

            StringBuilder sql = new StringBuilder("select user.name as username, \n" +
                    "user.email as useremail,\n" +
                    "role.name as rolename, permission.name as permissionname , role.id as roleId , permission.id as permissionId \n" +
                    "from user \n" +
                    "INNER JOIN user_role_relation ON user_role_relation.user_id = user.id \n" +
                    "INNER JOIN role ON user_role_relation.role_id = role.id \n" +
                    "INNER JOIN role_permission_relation ON role.id = role_permission_relation.role_id \n" +
                    "INNER JOIN permission ON permission.id = role_permission_relation.permission_id \n" +
                    "where user.email = ");

            sql.append("\"" + email + "\"");

            ResultSet rs = statement.executeQuery(sql.toString());

            List<Role> roles =new ArrayList<>();
            while (rs.next()) {
                if(rs.isFirst()) {
                    userDTO.setEmail(rs.getString("useremail"));
                    userDTO.setName(rs.getString("username"));
                }
                Role role = new Role();

                Permission permission = new Permission();
                role.setId(rs.getInt("roleId"));
                role.setName(rs.getString("rolename"));
                permission.setId(rs.getInt("permissionId"));
                permission.setName(rs.getString("permissionname"));
                if(roles.contains(role)){
                    roles.get(roles.indexOf(role)).getPermissions().add(permission);
                } else {
                    List<Permission> permissions = new ArrayList<>();
                    permissions.add(permission);
                    role.setPermissions(permissions);
                    roles.add(role);
                }

            }

            userDTO.setRoles(roles);

        } catch (SQLException e) {
            System.out.println("error occurred in UserDAOImpl getUserRolePermissionsByEmail " + e.getMessage());
            logger.error("error occurred in UserDAOImpl getUserRolePermissionsByEmail " + e.getMessage());
            throw e;
        }

        return userDTO;
    }

    @Override
    public void setUserRole(Integer userId, Integer roleId) throws SQLException  {

        try {
            String sql = "INSERT INTO user_role_relation (user_id, role_id) VALUES (?,?);";
            PreparedStatement statement1 = connection.prepareStatement(sql);
            statement1.setInt(1, userId );
            statement1.setInt(2, roleId );

            int affectedRows = statement1.executeUpdate();
            if (affectedRows == 0) {
                logger.error("Error with affectedRows in UserDAOImpl setUserRole");
                throw new SQLException("Error with affectedRows in UserDAOImpl setUserRole");
            }

        } catch (SQLException e) {
            System.out.println("error in UserDAOImpl setUserRole " + e.getMessage());
            logger.error("error in UserDAOImpl setUserRole " + e.getMessage());
            throw e;
        }
    }

}
