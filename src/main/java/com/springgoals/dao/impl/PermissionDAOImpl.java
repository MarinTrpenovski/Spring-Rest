package com.springgoals.dao.impl;
import com.springgoals.dao.SingletonConnection;
import com.springgoals.dao.PermissionDAO;
import com.springgoals.model.Permission;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;


public class PermissionDAOImpl implements PermissionDAO{

    private static final Logger logger = LogManager.getLogger(PermissionDAOImpl.class);

    static Connection connection;

    static {
        try {
            connection = SingletonConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Permission getById(Integer id) throws SQLException  {

        Permission permission = new Permission();
        try {
            connection = SingletonConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            StringBuilder sql = new StringBuilder("select * from permission where id = ");

            sql.append(id);
            ResultSet resultSet = statement.executeQuery(sql.toString());

            while (resultSet.next()) {
                permission.setId(resultSet.getInt("id"));
                permission.setName(resultSet.getString("name"));

            }
        } catch (SQLException e) {
            System.out.println("error occurred in PermissionDAOImpl getById " + e.getMessage());
            logger.error("error occurred in PermissionDAOImpl getById " + e.getMessage());
            throw e;

        }
        return permission;

    }

    @Override
    public List<Permission> getAll() throws SQLException  {

        List<Permission> permissionList = new ArrayList<>();
        try {

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from permission");
            while (rs.next()) {
                Permission permission = new Permission();
                permission.setName(rs.getString("name"));
                permission.setId(rs.getInt("id"));

                permissionList.add(permission);
            }
        } catch (SQLException e) {
            System.out.println("error occurred in PermissionDAOImpl getAll " + e.getMessage());
            logger.error("error occurred in PermissionDAOImpl getAll " + e.getMessage());
            throw e;

        }
        return permissionList;

    }


    @Override
    public void save(Permission permission)  throws SQLException {

        try {
            String sql = "INSERT INTO permission (name) VALUES (?)";
            PreparedStatement statement1 = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement1.setString(1, permission.getName());

            int affectedRows = statement1.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("error");
            }

        } catch (SQLException e) {
            System.out.println("error occurred in PermissionDAOImpl save " + e.getMessage());
            logger.error("error occurred in PermissionDAOImpl save " + e.getMessage());
            throw e;

        }

    }

    @Override
    public void update(Permission permission) throws SQLException  {

        try {
            connection = SingletonConnection.getInstance().getConnection();
            String sql = "UPDATE education.permission SET name=? WHERE id=?";
            PreparedStatement statement1 = connection.prepareStatement(sql);
            statement1.setString(1, permission.getName());


            int rowsUpdated = statement1.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing permission was updated");
            }

        } catch (SQLException e) {
            System.out.println("error occurred in PermissionDAOImpl update " + e.getMessage());
            logger.error("error occurred in PermissionDAOImpl update " + e.getMessage());
            throw e;
        }

    }

    @Override
    public void delete(Integer id)  throws SQLException {

        try {
            String sql = "DELETE FROM permission WHERE id=?";
            PreparedStatement statement1 = connection.prepareStatement(sql);
            statement1.setInt(1, id);

            int rowsUpdated = statement1.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("permission with id " + id + " was deleted");
            }

        } catch (SQLException e) {
            System.out.println("error occurred in PermissionDAOImpl delete " + e.getMessage());
            logger.error("error occurred in PermissionDAOImpl delete " + e.getMessage());
            throw e;

        }

    }

}
