package com.springgoals.dao.impl;

import com.springgoals.dao.FacultyDAO;
import com.springgoals.dao.SingletonConnection;
import com.springgoals.model.Faculty;
import com.springgoals.model.Subject;
import com.springgoals.model.dto.FacultySubjectDTO;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class FacultyDAOImpl implements FacultyDAO {

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
            }
        } catch (SQLException e) {
            System.out.println("error occured " + e.getMessage());
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
                faculty.setName(rs.getString("name"));
                faculty.setId(rs.getInt("id"));
                faculty.setLocation(rs.getString("location"));
                faculty.setStudy_field(rs.getString("study_field"));
                facultyList.add(faculty);
            }
        } catch (SQLException e) {
            System.out.println("error occured " + e.getMessage());
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
            System.out.println("error occured " + e.getMessage());
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
                faculty.setName(rs.getString("name"));
                faculty.setId(rs.getInt("id"));
                faculty.setLocation(rs.getString("location"));
                faculty.setStudy_field(rs.getString("study_field"));

                facultyList.add(faculty);
            }
        } catch (SQLException e) {
            System.out.println("error occured " + e.getMessage());
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
            System.out.println("error occured " + e.getMessage());
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
                throw new SQLException("Error in faculty save with affectedRows = statement1.executeUpdate()");
            }

        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
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
                throw new SQLException("error");
            }

            try (ResultSet generatedKeys = statement1.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("error2");
                }
            }
        } catch (SQLException e) {
            System.out.println("error occured " + e.getMessage());
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

        } catch (SQLException e) {
            System.out.println("Error during delete faculty " + e.getMessage());
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
            System.out.println("error occured " + e.getMessage());
            throw e;
        }


        return facultySubjectDTO;
    }
}
