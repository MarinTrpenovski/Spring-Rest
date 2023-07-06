package com.springgoals.dao.impl;

import com.springgoals.dao.ProfessorDAO;
import com.springgoals.dao.SingletonConnection;
import com.springgoals.model.Professor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository

public class ProfessorDAOImpl implements ProfessorDAO {

    static Connection connection;

    static {
        try {
            connection = SingletonConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Professor getById(Integer id) throws SQLException {

        Professor professor = new Professor();
        try {
            connection = SingletonConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            StringBuilder sql = new StringBuilder("select * from professor where id = ");
            sql.append(id);
            ResultSet resultSet = statement.executeQuery(sql.toString());

            while (resultSet.next()) {
                professor.setName(resultSet.getString("name"));
                professor.setId(resultSet.getInt("id"));
                professor.setSurname(resultSet.getString("surname"));
                professor.setPrimary_subject1(resultSet.getString("primary_subject1"));
                professor.setPrimary_subject2(resultSet.getString("primary_subject2"));
                professor.setAge(resultSet.getInt("age"));
                professor.setProfessor_faculty(resultSet.getInt("professor_faculty"));


            }
        } catch (SQLException e) {
            System.out.println("error occured " + e.getMessage());
            throw e;
        }
        return professor;
    }

    @Override
    public List<Professor> getAll() throws SQLException {

        List<Professor> professorList = new ArrayList<>();
        try {
            connection = SingletonConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from professor");
            while (rs.next()) {
                Professor professor = new Professor();
                professor.setName(rs.getString("name"));
                professor.setSurname(rs.getString("surname"));
                professor.setId(rs.getInt("id"));
                professor.setAge(rs.getInt("age"));
                professor.setPrimary_subject1(rs.getString("primary_subject1"));
                professor.setPrimary_subject2(rs.getString("primary_subject2"));
                professor.setProfessor_faculty(rs.getInt("professor_faculty"));

                professorList.add(professor);
            }
        } catch (SQLException e) {
            System.out.println("error occured " + e.getMessage());
            throw e;
        }
        return professorList;
    }

    @Override
    public Map<Integer, Professor> getMap() throws SQLException {

        Map<Integer, Professor> professorMap = new HashMap<>();
        try {
            connection = SingletonConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from professor");
            while (rs.next()) {

                Professor professor = new Professor();
                professor.setName(rs.getString("name"));
                professor.setId(rs.getInt("id"));
                professor.setSurname(rs.getString("surname"));
                professor.setAge(rs.getInt("age"));
                professor.setPrimary_subject1(rs.getString("primary_subject1"));
                professor.setPrimary_subject2(rs.getString("primary_subject2"));

                professorMap.put(professor.getId(), professor);
            }
        } catch (SQLException e) {
            System.out.println("error occured " + e.getMessage());
            throw e;
        }
        return professorMap;
    }

    @Override
    public List<Professor> searchProfessors(String sql)
            throws SQLException {
        List<Professor> professorList = new ArrayList<>();
        try {
            connection = SingletonConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                Professor professor = new Professor();

                professor.setId(rs.getInt("id"));
                professor.setName(rs.getString("name"));
                professor.setSurname(rs.getString("surname"));
                professor.setAge(rs.getInt("age"));
                professor.setPrimary_subject1(rs.getString("primary_subject1"));
                professor.setPrimary_subject2(rs.getString("primary_subject2"));
                professor.setProfessor_faculty(rs.getInt("professor_faculty"));

                professorList.add(professor);
            }
        } catch (SQLException e) {
            System.out.println("error" + e.getMessage());
            throw e;
        }
        return professorList;
    }

    @Override
    public void update(Professor professor) throws SQLException {

        try {
            String sql = "UPDATE professor  SET name=?, surname=?, primary_subject1=?, primary_subject2 =?, age =? WHERE id=?";
            PreparedStatement statement1 = connection.prepareStatement(sql);
            statement1.setString(1, professor.getName());
            statement1.setString(2, professor.getSurname());
            statement1.setString(3, professor.getPrimary_subject1());
            statement1.setString(4, professor.getPrimary_subject2());
            statement1.setInt(5, professor.getAge());
            statement1.setInt(6, professor.getId());

            int rowsUpdated = statement1.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing professor was updated");
            }

        } catch (SQLException e) {
            System.out.println("error occured " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void save(Professor professor) throws SQLException {

        try {
            String sql = "INSERT INTO professor (name, surname, primary_subject1, " +
                    "primary_subject2, age,  professor_faculty) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement1 = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement1.setString(1, professor.getName());
            statement1.setString(2, professor.getSurname());
            statement1.setString(3, professor.getPrimary_subject1());
            statement1.setString(4, professor.getPrimary_subject2());
            statement1.setInt(5, professor.getAge());
            statement1.setInt(6, 1);


            int affectedRows = statement1.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("error in DAO save, affectedRows");
            }

        } catch (SQLException e) {
            System.out.println("error " + e.getMessage());
            throw e;
        }

    }

    @Override
    public void delete(Integer id_deleting) throws SQLException {

        try {
            String sql = "DELETE FROM professor WHERE id=?";
            PreparedStatement statement1 = connection.prepareStatement(sql);
            statement1.setInt(1, id_deleting);

            int rowsUpdated = statement1.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("professor with id " + id_deleting + " was deleted");
            }

        } catch (SQLException e) {
            System.out.println("error occured " + e.getMessage());
            throw e;

        }
    }


}


