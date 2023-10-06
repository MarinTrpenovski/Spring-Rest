package com.springgoals.dao.impl;

import com.springgoals.controller.LogController;
import com.springgoals.dao.SingletonConnection;
import com.springgoals.dao.SubjectDAO;
import com.springgoals.model.Subject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository

public class SubjectDAOImpl implements SubjectDAO {

    private static final Logger logger = LogManager.getLogger(SubjectDAOImpl.class);

    static Connection connection;

    static {
        try {
            connection = SingletonConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Subject getById(Integer id) throws SQLException {

        Subject subject = new Subject();
        try {
            connection = SingletonConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            StringBuilder sql = new StringBuilder("select * from subject where id = ");
            sql.append(id);
            ResultSet resultSet = statement.executeQuery(sql.toString());

            while (resultSet.next()) {
                subject.setId(resultSet.getInt("id"));
                subject.setName(resultSet.getString("name"));
                subject.setSemester(resultSet.getString("semester"));
                subject.setCredits(resultSet.getInt("credits"));
                subject.setSubject_professor(resultSet.getInt("subject_professor"));
            }
        } catch (SQLException e) {
            System.out.println("error occurred in SubjectDAOImpl getById " + e.getMessage());
            logger.error("error occurred in SubjectDAOImpl getById " + e.getMessage());
            throw e;
        }

        return subject;

    }

    @Override
    public List<Subject> getAll() throws SQLException {

        List<Subject> subjectList = new ArrayList<>();
        try {
            connection = SingletonConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from subject");
            while (rs.next()) {
                Subject subject = new Subject();

                subject.setId(rs.getInt("id"));
                subject.setName(rs.getString("name"));
                subject.setSemester(rs.getString("semester"));
                subject.setCredits(rs.getInt("credits"));
                subject.setSubject_professor(rs.getInt("subject_professor"));

                subjectList.add(subject);
            }
        } catch (SQLException e) {
            System.out.println("error occurred in SubjectDAOImpl getAll " + e.getMessage());
            logger.error("error occurred in SubjectDAOImpl getAll " + e.getMessage());
            throw e;
        }
        return subjectList;
    }

    @Override
    public Map<Integer, Subject> getMap() throws SQLException {

        Map<Integer, Subject> subjectMap = new HashMap<>();
        try {
            connection = SingletonConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from subject");

            while (rs.next()) {
                Subject subject = new Subject();
                subject.setName(rs.getString("name"));
                subject.setId(rs.getInt("id"));
                subject.setSemester(rs.getString("semester"));
                subject.setCredits(rs.getInt("credits"));
                subjectMap.put(subject.getId(), subject);
            }
        } catch (SQLException e) {
            System.out.println("error occurred in SubjectDAOImpl getMap " + e.getMessage());
            logger.error("error occurred in SubjectDAOImpl getMap " + e.getMessage());
            throw e;
        }
        return subjectMap;
    }

    @Override
    public List<Subject> searchSubjects(String sql)
            throws SQLException {
        List<Subject> subjectList = new ArrayList<>();
        try {
            connection = SingletonConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                Subject subject = new Subject();

                subject.setId(rs.getInt("id"));
                subject.setName(rs.getString("name"));
                subject.setSemester(rs.getString("semester"));
                subject.setCredits(rs.getInt("credits"));
                subject.setSubject_professor(rs.getInt("subject_professor"));

                subjectList.add(subject);
            }
        } catch (SQLException e) {
            System.out.println("error occurred in SubjectDAOImpl searchSubjects " + e.getMessage());
            logger.error("error occurred in SubjectDAOImpl searchSubjects " + e.getMessage());
            throw e;
        }
        return subjectList;
    }

    @Override
    public void update(Subject subject) throws SQLException {

        try {
            String sql = "UPDATE subject SET name=?, semester=?, credits=? WHERE id=?";
            PreparedStatement statement1 = connection.prepareStatement(sql);
            statement1.setString(1, subject.getName());
            statement1.setString(2, subject.getSemester());
            statement1.setInt(3, subject.getCredits());
            statement1.setInt(4, subject.getId());

            int rowsUpdated = statement1.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing subject was updated");
            }

        } catch (SQLException e) {
            System.out.println("error occurred in SubjectDAOImpl update " + e.getMessage());
            logger.error("error occurred in SubjectDAOImpl update " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void save(Subject subject) throws SQLException {

        try {
            String sql = "INSERT INTO subject (name, semester, credits, subject_professor) VALUES (?, ?, ?, ?)";
            PreparedStatement statement1 = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement1.setString(1, subject.getName());
            statement1.setString(2, subject.getSemester());
            statement1.setInt(3, subject.getCredits());
            statement1.setInt(4, 1);


            int affectedRows = statement1.executeUpdate();

            if (affectedRows == 0) {
                logger.error("Error with affectedRows in SubjectDAOImpl save");
                throw new SQLException("Error with affectedRows in SubjectDAOImpl save");
            }

        } catch (SQLException e) {
            System.out.println("error occurred in SubjectDAOImpl save " + e.getMessage());
            logger.error("error occurred in SubjectDAOImpl save " + e.getMessage());
            throw e;
        }
    }

    @Override
    public Integer saveReturnId(Subject subject) throws SQLException {
        Integer id;

        try {
            String sql = "INSERT INTO subject (name, semester, credits, subject_professor) VALUES (?, ?, ?, ?)";
            PreparedStatement statement1 = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement1.setString(1, subject.getName());
            statement1.setString(2, subject.getSemester());
            statement1.setInt(3, subject.getCredits());


            int affectedRows = statement1.executeUpdate();

            if (affectedRows == 0) {
                logger.error("Error with affectedRows in SubjectDAOImpl saveReturnId");
                throw new SQLException("Error with affectedRows in SubjectDAOImpl saveReturnId");
            }

            try (ResultSet generatedKeys = statement1.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);
                } else {
                    logger.error("Error with generatedKeys in SubjectDAOImpl saveReturnId");
                    throw new SQLException("Error with generatedKeys in SubjectDAOImpl saveReturnId");
                }
            }
        } catch (SQLException e) {
            System.out.println("error occurred in SubjectDAOImpl saveReturnId " + e.getMessage());
            logger.error("error occurred in SubjectDAOImpl saveReturnId " + e.getMessage());
            throw e;

        }
        return id;
    }

    @Override
    public void delete(Integer id_deleting) throws SQLException {
        try {
            String sql = "DELETE FROM subject WHERE id=?";
            PreparedStatement statement1 = connection.prepareStatement(sql);
            statement1.setInt(1, id_deleting);

            int rowsUpdated = statement1.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("subject with id " + id_deleting + " was deleted");
            }

        } catch (SQLException e) {
            System.out.println("error occurred in SubjectDAOImpl delete " + e.getMessage());
            logger.error("error occurred in SubjectDAOImpl delete " + e.getMessage());
            throw e;
        }

    }

    @Override
    public void removeSubjectStudentRelation(Integer studentId, Integer subjectId) throws SQLException {
        try {
            String sql = "DELETE FROM student_subject_relation" +
                    " WHERE  student_id=? AND subject_id=?";
            PreparedStatement statement1 = connection.prepareStatement(sql);
            statement1.setInt(1, studentId);
            statement1.setInt(2, subjectId);

            int rowsUpdated = statement1.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("deleteDTO rowsUpdated :Subject_id = " + subjectId + " student_id = " + studentId + " was deleted");

            }

        } catch (SQLException e) {
            System.out.println("error in SubjectDAOImpl removeSubjectStudentRelation " + e.getMessage());
            logger.error("error in SubjectDAOImpl removeSubjectStudentRelation " + e.getMessage());
            throw e;
        }

    }

    @Override
    public Integer getSubjectStudents(Integer studentId, Integer subjectId) throws SQLException {

        Integer numberOfStudents;
        try {
            connection = SingletonConnection.getInstance().getConnection();

            String sql2 = "Select  count(student_id) as counterOfStudents FROM student_subject_relation" +
                    " WHERE subject_id=?;";

            PreparedStatement statement2 = connection.prepareStatement(sql2);
            statement2.setInt(1, studentId);

            ResultSet resultSet = statement2.executeQuery();

            numberOfStudents = resultSet.getInt("counterOfStudents");

            System.out.println("number of students is " + numberOfStudents);
        } catch (SQLException e) {
            System.out.println("error in SubjectDAOImpl getSubjectStudents " + e.getMessage());
            logger.error("error in SubjectDAOImpl getSubjectStudents " + e.getMessage());
            throw e;
        }
        return numberOfStudents;
    }
}


