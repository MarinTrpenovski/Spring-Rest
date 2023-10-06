package com.springgoals.dao.impl;

import com.springgoals.dao.SingletonConnection;
import com.springgoals.dao.RoleDAO;
import com.springgoals.model.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class RoleDAOImpl implements RoleDAO{

    private static final Logger logger = LogManager.getLogger(RoleDAOImpl.class);
    static Connection connection;

    static {
        try {
            connection = SingletonConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Role getById(Integer id) throws SQLException  {

        Role role = new Role();
        try {
            connection = SingletonConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            StringBuilder sql = new StringBuilder("select * from role where id = ");

            sql.append(id);
            ResultSet resultSet = statement.executeQuery(sql.toString());

            while (resultSet.next()) {
                role.setId(resultSet.getInt("id"));
                role.setName(resultSet.getString("name"));

            }
        } catch (SQLException e) {
            System.out.println("error occurred in RoleDAOImpl getById " + e.getMessage());
            logger.error("error occurred in RoleDAOImpl getById " + e.getMessage());
            throw e;

        }
        return role;

    }

    @Override
    public List<Role> getAll() throws SQLException  {

        List<Role> roleList = new ArrayList<>();
        try {

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from role");
            while (rs.next()) {
                Role role = new Role();
                role.setName(rs.getString("name"));
                role.setId(rs.getInt("id"));

                roleList.add(role);
            }
        } catch (SQLException e) {
            System.out.println("error occurred in RoleDAOImpl getAll " + e.getMessage());
            logger.error("error occurred in RoleDAOImpl getAll " + e.getMessage());
            throw e;

        }
        return roleList;

    }


    @Override
    public void save(Role role)  throws SQLException {

        try {
            String sql = "INSERT INTO role (name) VALUES (?)";
            PreparedStatement statement1 = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement1.setString(1, role.getName());

            int affectedRows = statement1.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("error");
            }

        } catch (SQLException e) {
            System.out.println("error occurred in RoleDAOImpl save " + e.getMessage());
            logger.error("error occurred in RoleDAOImpl save " + e.getMessage());
            throw e;

        }

    }

    @Override
    public void update(Role role) throws SQLException  {

        try {
            connection = SingletonConnection.getInstance().getConnection();
            String sql = "UPDATE education.role SET name=? WHERE id=?";
            PreparedStatement statement1 = connection.prepareStatement(sql);
            statement1.setString(1, role.getName());


            int rowsUpdated = statement1.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing role was updated");
            }

        } catch (SQLException e) {
            System.out.println("error occurred in RoleDAOImpl update " + e.getMessage());
            logger.error("error occurred in RoleDAOImpl update " + e.getMessage());
            throw e;
        }

    }

    @Override
    public void delete(Integer id)  throws SQLException {

        try {
            String sql = "DELETE FROM role WHERE id=?";
            PreparedStatement statement1 = connection.prepareStatement(sql);
            statement1.setInt(1, id);

            int rowsUpdated = statement1.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("role with id " + id + " was deleted");
            }

        } catch (SQLException e) {
            System.out.println("error occurred in RoleDAOImpl delete " + e.getMessage());
            logger.error("error occurred in RoleDAOImpl delete " + e.getMessage());
            throw e;

        }

    }


}
