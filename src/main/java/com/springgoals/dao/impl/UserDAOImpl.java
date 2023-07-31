package com.springgoals.dao.impl;

import com.springgoals.dao.SingletonConnection;
import com.springgoals.dao.UserDAO;
import com.springgoals.model.User;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

@Repository
public class UserDAOImpl implements UserDAO {

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
            System.out.println("error occurred in user getById " + e.getMessage());
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
            System.out.println("error occurred in user getAll " + e.getMessage());
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
                throw new SQLException("error");
            }

        } catch (SQLException e) {
            System.out.println("error occurred " + e.getMessage());
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
            System.out.println("error in DAO update" + e.getMessage());
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
            System.out.println("error occurred " + e.getMessage());
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
            System.out.println("error" + e.getMessage());
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
            System.out.println("error: " + "Invalid username or password" + e.getMessage());
            throw e;
        }
        return user;

    }

    /*
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
            System.out.println("error occurred in user getById " + e.getMessage());
            throw e;

        }
        return user;

    }
    * */

}
