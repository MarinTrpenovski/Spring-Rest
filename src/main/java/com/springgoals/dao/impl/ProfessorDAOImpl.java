package com.springgoals.dao.impl;

import com.springgoals.dao.ProfessorDAO;
import com.springgoals.dao.SingletonConnection;
import com.springgoals.model.Professor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository

public class ProfessorDAOImpl implements ProfessorDAO {

    private static final Logger logger = LogManager.getLogger(ProfessorDAOImpl.class);

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
                professor.setImagePath(resultSet.getString("photo"));
            }
        } catch (SQLException e) {
            System.out.println("error occurred in ProfessorDAOImpl getById " + e.getMessage());
            logger.error("error occurred in ProfessorDAOImpl getById " + e.getMessage());
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
                professor.setImagePath(rs.getString("photo"));
                professorList.add(professor);
            }
        } catch (SQLException e) {
            System.out.println("error occurred in ProfessorDAOImpl getAll " + e.getMessage());
            logger.error("error occurred in ProfessorDAOImpl getAll " + e.getMessage());
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
            System.out.println("error occurred in ProfessorDAOImpl getMap " + e.getMessage());
            logger.error("error occurred in ProfessorDAOImpl getMap " + e.getMessage());
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
                professor.setImagePath(rs.getString("photo"));
                professor.setProfessor_faculty(rs.getInt("professor_faculty"));

                professorList.add(professor);
            }
        } catch (SQLException e) {
            System.out.println("error occurred in ProfessorDAOImpl searchProfessors " + e.getMessage());
            logger.error("error occurred in ProfessorDAOImpl searchProfessors " + e.getMessage());
            throw e;
        }
        return professorList;
    }


    @Override
    public void save(Professor professor) throws SQLException {

        try {
            String sql = "INSERT INTO professor (name, surname, primary_subject1, " +
                    "primary_subject2, age, photo, professor_faculty) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement1 = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement1.setString(1, professor.getName());
            statement1.setString(2, professor.getSurname());
            statement1.setString(3, professor.getPrimary_subject1());
            statement1.setString(4, professor.getPrimary_subject2());
            statement1.setInt(5, professor.getAge());
            statement1.setString(6, professor.getImagePath());
            statement1.setInt(7, 1);

            int affectedRows = statement1.executeUpdate();

            if (affectedRows == 0) {
                logger.error("Error with affectedRows in ProfessorDAOImpl save");
                throw new SQLException("Error with affectedRows in ProfessorDAOImpl save");
            }

        } catch (SQLException e) {
            System.out.println("error occurred in ProfessorDAOImpl save " + e.getMessage());
            logger.error("error occurred in ProfessorDAOImpl save " + e.getMessage());
            throw e;
        }
    }

    @Override
    public Integer saveReturnId(Professor professor) throws SQLException {

        Integer id;

        try {
            String sql = "INSERT INTO professor (name, surname, primary_subject1, " +
                    "primary_subject2, age, photo, professor_faculty) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement1 = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement1.setString(1, professor.getName());
            statement1.setString(2, professor.getSurname());
            statement1.setString(3, professor.getPrimary_subject1());
            statement1.setString(4, professor.getPrimary_subject2());
            statement1.setInt(5, professor.getAge());
            statement1.setString(6, professor.getImagePath());
            statement1.setInt(7, 1);

            int affectedRows = statement1.executeUpdate();

            if (affectedRows == 0) {
                logger.error("Error with affectedRows in ProfessorDAOImpl saveReturnId");
                throw new SQLException("Error with affectedRows in ProfessorDAOImpl saveReturnId");
            }

            try (ResultSet generatedKeys = statement1.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);
                } else {
                    logger.error("Error with generatedKeys in ProfessorDAOImpl saveReturnId");
                    throw new SQLException("Error with generatedKeys in ProfessorDAOImpl saveReturnId");
                }
            }
        } catch (SQLException e) {
            System.out.println("error occurred in ProfessorDAOImpl saveReturnId " + e.getMessage());
            logger.error("error occurred in ProfessorDAOImpl saveReturnId " + e.getMessage());
            throw e;
        }
        return id;
    }

    @Override
    public void update(Professor professor) throws SQLException {

        try {
            String sql = "UPDATE professor  SET name=?, surname=?, primary_subject1=?, primary_subject2 =?, age =?" +
                    ", photo=?  WHERE id=?";
            PreparedStatement statement1 = connection.prepareStatement(sql);
            statement1.setString(1, professor.getName());
            statement1.setString(2, professor.getSurname());
            statement1.setString(3, professor.getPrimary_subject1());
            statement1.setString(4, professor.getPrimary_subject2());
            statement1.setInt(5, professor.getAge());
            statement1.setString(6, professor.getImagePath());
            statement1.setInt(7, professor.getId());

            int rowsUpdated = statement1.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing professor was updated");
            }

        } catch (SQLException e) {
            System.out.println("error occurred in ProfessorDAOImpl update " + e.getMessage());
            logger.error("error occurred in ProfessorDAOImpl update " + e.getMessage());
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
            System.out.println("error occurred in ProfessorDAOImpl delete " + e.getMessage());
            logger.error("error occurred in ProfessorDAOImpl delete " + e.getMessage());
            throw e;

        }
    }


    @Override
    public void deleteImages() throws SQLException {
        try {
            String sql = "INSERT INTO professor ( photo ) VALUES (?)";
            PreparedStatement statement1 = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement1.setString(1, null);

            int affectedRows = statement1.executeUpdate();

            if (affectedRows == 0) {
                logger.error("Error with affectedRows in ProfessorDAOImpl deleteImages");
                throw new SQLException("Error with affectedRows in ProfessorDAOImpl deleteImages");
            }

        } catch (SQLException e) {
            System.out.println("error SQLException in ProfessorDAOImpl deleteImages " + e.getMessage());
            logger.error("error SQLException in ProfessorDAOImpl deleteImages " + e.getMessage());
            throw e;
        }
    }
}


