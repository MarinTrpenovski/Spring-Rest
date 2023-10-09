package com.springgoals.dao.impl;

import com.springgoals.controller.LogController;
import com.springgoals.dao.FacultyDAO;
import com.springgoals.dao.SingletonConnection;
import com.springgoals.model.Faculty;
import com.springgoals.model.Subject;
import com.springgoals.model.dto.FacultySubjectDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class FacultyDAOImpl implements FacultyDAO {

    private static final Logger logger = LogManager.getLogger(FacultyDAOImpl.class);
    static Connection connection;

    static {
        try {
            connection = SingletonConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Faculty getById(Integer id) throws SQLException {
        Faculty faculty = new Faculty();
        try {
            connection = SingletonConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            StringBuilder sql = new StringBuilder("select * from faculty where id = ");
            sql.append(id);
            ResultSet resultSet = statement.executeQuery(sql.toString());

            while (resultSet.next()) {
                faculty.setName(resultSet.getString("name"));
                faculty.setId(resultSet.getInt("id"));
                faculty.setLocation(resultSet.getString("location"));
                faculty.setStudy_field(resultSet.getString("study_field"));
                faculty.setUniversity_id(resultSet.getInt("university_id"));

            }
        } catch (SQLException e) {
            System.out.println("error occurred in FacultyDAOImpl getById " + e.getMessage());
            logger.error("error occurred in FacultyDAOImpl getById " + e.getMessage());
            throw e;
        }

        return faculty;
    }

    @Override
    public List<Faculty> getAll() throws SQLException {
        List<Faculty> facultyList = new ArrayList<>();
        try {
            connection = SingletonConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from faculty");
            while (rs.next()) {
                Faculty faculty = new Faculty();
                faculty.setId(rs.getInt("id"));
                faculty.setName(rs.getString("name"));
                faculty.setLocation(rs.getString("location"));
                faculty.setStudy_field(rs.getString("study_field"));
                faculty.setUniversity_id(rs.getInt("university_id"));

                facultyList.add(faculty);
            }
        } catch (SQLException e) {
            System.out.println("error occurred in FacultyDAOImpl getAll " + e.getMessage());
            logger.error("error occurred in FacultyDAOImpl getAll " + e.getMessage());
            throw e;
        }
        return facultyList;
    }

    @Override
    public Map<Integer, Faculty> getMap() throws SQLException {

        Map<Integer, Faculty> facultyMap = new HashMap<>();
        try {
            connection = SingletonConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from faculty");
            while (rs.next()) {
                Faculty faculty = new Faculty();
                faculty.setName(rs.getString("name"));
                faculty.setId(rs.getInt("id"));
                faculty.setLocation(rs.getString("location"));
                faculty.setStudy_field(rs.getString("study_field"));
                facultyMap.put(faculty.getId(), faculty);
            }
        } catch (SQLException e) {
            System.out.println("error occurred in FacultyDAOImpl getMap " + e.getMessage());
            logger.error("error occurred in FacultyDAOImpl getMap " + e.getMessage());
            throw e;
        }
        return facultyMap;
    }

    @Override
    public List<Faculty> searchFaculties(String sql)
            throws SQLException {
        List<Faculty> facultyList = new ArrayList<>();

        try {
            connection = SingletonConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                Faculty faculty = new Faculty();
                faculty.setId(rs.getInt("id"));
                faculty.setName(rs.getString("name"));
                faculty.setLocation(rs.getString("location"));
                faculty.setStudy_field(rs.getString("study_field"));
                faculty.setUniversity_id(rs.getInt("university_id"));

                facultyList.add(faculty);
            }
        } catch (SQLException e) {
            System.out.println("error occurred in FacultyDAOImpl searchFaculties " + e.getMessage());
            logger.error("error occurred in FacultyDAOImpl searchFaculties " + e.getMessage());
            throw e;

        }

        return facultyList;
    }

    @Override
    public void update(Faculty faculty) throws SQLException {
        try {
            String sql = "UPDATE faculty SET name=?, location=?, study_field=? WHERE id=?";
            PreparedStatement statement1 = connection.prepareStatement(sql);
            statement1.setString(1, faculty.getName());
            statement1.setString(2, faculty.getLocation());
            statement1.setString(3, faculty.getStudy_field());
            statement1.setInt(4, faculty.getId());

            int rowsUpdated = statement1.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing faculty was updated!");
            }

        } catch (SQLException e) {
            System.out.println("error occurred in FacultyDAOImpl update " + e.getMessage());
            logger.error("error occurred in FacultyDAOImpl update " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void save(Faculty faculty) throws SQLException {

        try {
            String sql = "INSERT INTO faculty(name, location, study_field, university_id) VALUES (?, ?, ?, ?)";
            PreparedStatement statement1 = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement1.setString(1, faculty.getName());
            statement1.setString(2, faculty.getLocation());
            statement1.setString(3, faculty.getStudy_field());
            statement1.setInt(4, faculty.getUniversity_id());

            int affectedRows = statement1.executeUpdate();
            if (affectedRows == 0) {
                logger.error("Error with affectedRows in FacultyDAOImpl save");
                throw new SQLException("Error with affectedRows in FacultyDAOImpl save");
            }

        } catch (SQLException e) {
            System.out.println("Error in FacultyDAOImpl save: " + e.getMessage());
            logger.error("Error in FacultyDAOImpl save: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public Integer saveReturnId(Faculty faculty) throws SQLException {

        Integer id;

        try {
            String sql = "INSERT INTO faculty (name, location, study_field,university_id) VALUES (?, ?, ?,?)";
            PreparedStatement statement1 = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement1.setString(1, faculty.getName());
            statement1.setString(2, faculty.getLocation());
            statement1.setString(3, faculty.getStudy_field());
            statement1.setInt(4, faculty.getUniversity_id());

            int affectedRows = statement1.executeUpdate();

            if (affectedRows == 0) {
                logger.error("Error with affectedRows in FacultyDAOImpl saveReturnId");
                throw new SQLException("Error with affectedRows in FacultyDAOImpl saveReturnId");
            }

            try (ResultSet generatedKeys = statement1.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);
                } else {
                    logger.error("Error with generatedKeys in FacultyDAOImpl saveReturnId");
                    throw new SQLException("Error with generatedKeys in FacultyDAOImpl saveReturnId");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in FacultyDAOImpl saveReturnId: " + e.getMessage());
            logger.error("Error in FacultyDAOImpl saveReturnId: " + e.getMessage());
            throw e;
        }
        return id;

    }

    @Override
    public void delete(Integer id_deleting) throws SQLException {
        try {
            String sql = "DELETE FROM faculty WHERE id=?";
            PreparedStatement statement1 = connection.prepareStatement(sql);
            statement1.setInt(1, id_deleting);

            int rowsUpdated = statement1.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("faculty with id " + id_deleting + " was deleted");
            }

        } catch (SQLException e) {
            System.out.println("Error in FacultyDAOImpl delete " + e.getMessage());
            logger.error("Error in FacultyDAOImpl delete " + e.getMessage());
            throw e;
        }
    }

    @Override
    public FacultySubjectDTO getSubjectsByFacId(Integer facultyId) throws SQLException {

        FacultySubjectDTO facultySubjectDTO = new FacultySubjectDTO();

        List<Subject> subjectList = new ArrayList<>();

        try {
            connection = SingletonConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();

            StringBuilder sql = new StringBuilder("select su.*,f.name as fname from subject as su\n" +
                    "inner join professor on professor.id = su.subject_professor\n" +
                    "inner join faculty as f on f.id = professor.professor_faculty\n" +
                    "WHERE f.id =");

            sql.append(facultyId);

            ResultSet rs = statement.executeQuery(sql.toString());

            while (rs.next()) {
                Subject subject = new Subject();

                subject.setId(rs.getInt("id"));
                subject.setName(rs.getString("name"));
                subject.setCredits(rs.getInt("credits"));
                subject.setSemester(rs.getString("semester"));

                facultySubjectDTO.setFacultyName(rs.getString("fname"));

                subjectList.add(subject);
            }

            facultySubjectDTO.setFacultyId(facultyId);
            facultySubjectDTO.setSubjectList(subjectList);
            facultySubjectDTO.setLengthOfList(subjectList.size());

        } catch (SQLException e) {
            System.out.println("Error in FacultyDAOImpl getSubjectsByFacId: " + e.getMessage());
            logger.error("Error in FacultyDAOImpl getSubjectsByFacId: " + e.getMessage());
            throw e;
        }

        return facultySubjectDTO;
    }
}
