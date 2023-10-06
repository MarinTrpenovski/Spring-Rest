package com.springgoals.dao.impl;

import com.springgoals.controller.LogController;
import com.springgoals.dao.SingletonConnection;
import com.springgoals.dao.UniversityDAO;
import com.springgoals.model.Faculty;
import com.springgoals.model.University;
import com.springgoals.model.dto.UniversityFacultyDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UniversityDAOImpl implements UniversityDAO {

    private static final Logger logger = LogManager.getLogger(UniversityDAOImpl.class);
    static Connection connection;

    static {
        try {
            connection = SingletonConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public University getById(Integer id) throws SQLException {

        University university = new University();
        try {
            connection = SingletonConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            StringBuilder sql = new StringBuilder("select * from university where id = ");

            sql.append(id);
            ResultSet resultSet = statement.executeQuery(sql.toString());

            while (resultSet.next()) {
                university.setId(resultSet.getInt("id"));
                university.setName(resultSet.getString("name"));
                university.setDescription(resultSet.getString("description"));

            }
        } catch (SQLException e) {
            System.out.println("error occurred in UniversityDAOImpl getById " + e.getMessage());
            logger.error("error occurred in UniversityDAOImpl getById " + e.getMessage());
            throw e;

        }
        return university;

    }

    @Override
    public List<University> getAll() throws SQLException {

        List<University> universityList = new ArrayList<>();
        try {

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from university");
            while (rs.next()) {
                University university = new University();
                university.setName(rs.getString("name"));
                university.setId(rs.getInt("id"));
                university.setDescription(rs.getString("description"));

                universityList.add(university);
            }
        } catch (SQLException e) {
            System.out.println("error occurred in UniversityDAOImpl getAll " + e.getMessage());
            logger.error("error occurred in UniversityDAOImpl getAll " + e.getMessage());
            throw e;

        }
        return universityList;

    }

    @Override
    public Map<Integer, University> getMap() throws SQLException {

        Map<Integer, University> universityMap = new HashMap<>();
        try {
            connection = SingletonConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from university");
            while (rs.next()) {
                University university = new University();
                university.setName(rs.getString("name"));
                university.setId(rs.getInt("id"));
                university.setDescription(rs.getString("description"));
                universityMap.put(university.getId(), university);
            }
        } catch (SQLException e) {
            System.out.println("error occurred in UniversityDAOImpl getMap " + e.getMessage());
            logger.error("error occurred in UniversityDAOImpl getMap " + e.getMessage());
            throw e;
        }
        return universityMap;
    }

    @Override
    public List<University> searchUniversities(String sql)
            throws SQLException {
        List<University> universityList = new ArrayList<>();
        try {
            connection = SingletonConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);


            while (rs.next()) {
                University university = new University();

                university.setId(rs.getInt("id"));
                university.setName(rs.getString("name"));
                university.setDescription(rs.getString("description"));

                universityList.add(university);
            }
        } catch (SQLException e) {
            System.out.println("error occurred in UniversityDAOImpl searchUniversities " + e.getMessage());
            logger.error("error occurred in UniversityDAOImpl searchUniversities " + e.getMessage());
            throw e;
        }
        return universityList;
    }

    @Override
    public void update(University university) throws SQLException {

        try {
            connection = SingletonConnection.getInstance().getConnection();
            String sql = "UPDATE education.university SET name=?, description=? WHERE id=?";
            PreparedStatement statement1 = connection.prepareStatement(sql);
            statement1.setString(1, university.getName());
            statement1.setString(2, university.getDescription());
            statement1.setInt(3, university.getId());


            int rowsUpdated = statement1.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing university was updated");
            }

        } catch (SQLException e) {
            System.out.println("error occurred in UniversityDAOImpl update " + e.getMessage());
            logger.error("error occurred in UniversityDAOImpl update " + e.getMessage());
            throw e;
        }

    }

    @Override
    public void save(University university) throws SQLException {

        try {
            String sql = "INSERT INTO university (name, description) VALUES (?, ?)";
            PreparedStatement statement1 = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement1.setString(1, university.getName());
            statement1.setString(2, university.getDescription());


            int affectedRows = statement1.executeUpdate();

            if (affectedRows == 0) {
                logger.error("Error with affectedRows in UniversityDAOImpl save");
                throw new SQLException("Error with affectedRows in UniversityDAOImpl save");
            }

        } catch (SQLException e) {
            System.out.println("error occurred in UniversityDAOImpl save " + e.getMessage());
            logger.error("error occurred in UniversityDAOImpl save " + e.getMessage());
            throw e;

        }

    }

    @Override
    public Integer saveReturnId(University university) throws SQLException {

        Integer id;

        try {
            String sql = "INSERT INTO university (name, description) VALUES (?, ?)";
            PreparedStatement statement1 = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement1.setString(1, university.getName());
            statement1.setString(2, university.getDescription());

            int affectedRows = statement1.executeUpdate();

            if (affectedRows == 0) {
                logger.error("Error with affectedRows in UniversityDAOImpl saveReturnId");
                throw new SQLException("Error with affectedRows in UniversityDAOImpl saveReturnId");
            }

            try (ResultSet generatedKeys = statement1.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);
                } else {
                    logger.error("Error with generatedKeys in UniversityDAOImpl saveReturnId");
                    throw new SQLException("Error with generatedKeys in UniversityDAOImpl saveReturnId");
                }
            }
        } catch (SQLException e) {
            System.out.println("error occurred in UniversityDAOImpl saveReturnId " + e.getMessage());
            logger.error("error occurred in UniversityDAOImpl saveReturnId " + e.getMessage());
            throw e;
        }
        return id;
    }

    @Override
    public void delete(Integer id_deleting) throws SQLException {
        try {
            String sql = "DELETE FROM university WHERE id=?";
            PreparedStatement statement1 = connection.prepareStatement(sql);
            statement1.setInt(1, id_deleting);

            int rowsUpdated = statement1.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("university with id " + id_deleting + " was deleted");
            }

        } catch (SQLException e) {
            System.out.println("error occurred in UniversityDAOImpl delete " + e.getMessage());
            logger.error("error occurred in UniversityDAOImpl delete " + e.getMessage());
            throw e;

        }
    }

    @Override
    public UniversityFacultyDTO getFacultiesByUniId(Integer universityId) throws SQLException {

        UniversityFacultyDTO universityFacultyDTO = new UniversityFacultyDTO();

        List<Faculty> facultyList = new ArrayList<>();

        try {
            connection = SingletonConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();

            StringBuilder sql = new StringBuilder("select  university.name as uname," +
                    "faculty.id as fid, faculty.name as fname,\n" +
                    "faculty.location as flocation, faculty.study_field as fstudy_field\n" +
                    "from faculty inner join university\n" +
                    " on faculty.university_id = university.id\n" +
                    " where faculty.university_id =");

            sql.append(universityId);

            ResultSet rs = statement.executeQuery(sql.toString());

            while (rs.next()) {
                Faculty faculty = new Faculty();

                faculty.setId(rs.getInt("fid"));
                faculty.setName(rs.getString("fname"));
                faculty.setLocation(rs.getString("flocation"));
                faculty.setStudy_field(rs.getString("fstudy_field"));

                universityFacultyDTO.setUniversityName(rs.getString("uname"));

                facultyList.add(faculty);
            }

            universityFacultyDTO.setUniversityId(universityId);
            universityFacultyDTO.setFacultyList(facultyList);
            universityFacultyDTO.setLengthOfList(facultyList.size());

        } catch (SQLException e) {
            System.out.println("error in UniversityDAOImpl getFacultiesByUniId " + e.getMessage());
            logger.error("error in UniversityDAOImpl getFacultiesByUniId " + e.getMessage());
            throw e;
        }

        return universityFacultyDTO;
    }


}




