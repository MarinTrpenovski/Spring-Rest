package com.springgoals.dao.impl;

import com.springgoals.dao.SingletonConnection;
import com.springgoals.dao.StudentDAO;
import com.springgoals.model.Student;
import com.springgoals.model.Subject;
import com.springgoals.model.dto.StudentSubjectDTO;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository

public class StudentDAOImpl implements StudentDAO {

    static Connection connection;

    static {
        try {
            connection = SingletonConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Student getById(Integer id) throws SQLException {

        Student student = new Student();
        try {
            connection = SingletonConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            StringBuilder sql = new StringBuilder("select * from student where id = ");
            sql.append(id);
            ResultSet resultSet = statement.executeQuery(sql.toString());

            while (resultSet.next()) {
                student.setId(resultSet.getInt("id"));
                student.setName(resultSet.getString("name"));
                student.setSurname(resultSet.getString("surname"));
                student.setLocation(resultSet.getString("location"));
                student.setIndeks(resultSet.getInt("indeks"));
            }
        } catch (SQLException e) {
            System.out.println("error occured " + e.getMessage());
            throw e;
        }
        return student;
    }

    @Override
    public List<Student> getAll() throws SQLException {

        List<Student> studentList = new ArrayList<>();
        try {
            connection = SingletonConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from student");
            while (rs.next()) {
                Student student = new Student();

                student.setId(rs.getInt("id"));
                student.setIndeks(rs.getInt("indeks"));
                student.setName(rs.getString("name"));
                student.setSurname(rs.getString("surname"));
                student.setLocation(rs.getString("location"));
                studentList.add(student);
            }
        } catch (SQLException e) {
            System.out.println("error occured " + e.getMessage());
            throw e;
        }
        return studentList;
    }

    @Override
    public Map<Integer, Student> getMap() throws SQLException {

        Map<Integer, Student> studentMap = new HashMap<>();
        try {
            connection = SingletonConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from student");
            while (rs.next()) {
                Student student = new Student();
                student.setName(rs.getString("name"));
                student.setId(rs.getInt("id"));
                student.setSurname(rs.getString("surname"));
                student.setLocation(rs.getString("location"));
                student.setIndeks(rs.getInt("indeks"));
                studentMap.put(student.getId(), student);
            }
        } catch (SQLException e) {
            System.out.println("error occured " + e.getMessage());
            throw e;
        }
        return studentMap;
    }

    @Override
    public List<Student> searchStudents(String sql)
            throws SQLException {
        List<Student> studentList = new ArrayList<>();
        try {
            connection = SingletonConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                Student student = new Student();

                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setSurname(rs.getString("surname"));
                student.setLocation(rs.getString("location"));
                student.setIndeks(rs.getInt("indeks"));

                studentList.add(student);
            }
        } catch (SQLException e) {
            System.out.println("error" + e.getMessage());
            throw e;
        }
        return studentList;
    }

    @Override
    public void update(Student student) throws SQLException {

        try {
            String sql = "UPDATE student SET name=?, surname=?, indeks=? ,location=? WHERE id=?";
            PreparedStatement statement1 = connection.prepareStatement(sql);
            statement1.setString(1, student.getName());
            statement1.setString(2, student.getSurname());
            statement1.setInt(3, student.getIndeks());
            statement1.setString(4, student.getLocation());
            statement1.setInt(5, student.getId());

            int rowsUpdated = statement1.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing student was updated");
            }

        } catch (SQLException e) {
            System.out.println("error occured " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void save(Student student) throws SQLException {

        try {
            String sql = "INSERT INTO student (name, surname, indeks, location) VALUES (?, ?, ?, ?)";
            PreparedStatement statement1 = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement1.setString(1, student.getName());
            statement1.setString(2, student.getSurname());
            statement1.setInt(3, student.getIndeks());
            statement1.setString(4, student.getLocation());

            int affectedRows = statement1.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("error");
            }

        } catch (SQLException e) {
            System.out.println("error occured " + e.getMessage());
            throw e;
        }
    }

    @Override
    public Integer saveReturnId(Student student) throws SQLException {

        Integer id ;

        try {
            String sql = "INSERT INTO student (name, surname, indeks, location) VALUES (?, ?, ?, ?)";
            PreparedStatement statement1 = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement1.setString(1, student.getName());
            statement1.setString(2, student.getSurname());
            statement1.setString(4, student.getLocation());
            statement1.setInt(3, student.getIndeks());

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
            String sql = "DELETE FROM student WHERE id=?";
            PreparedStatement statement1 = connection.prepareStatement(sql);
            statement1.setInt(1, id_deleting);

            int rowsUpdated = statement1.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("student with id " + id_deleting + " was deleted");
            }

        } catch (SQLException e) {
            System.out.println("error occured in delete student: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public StudentSubjectDTO getSubjectsByStudId(Integer studentId) throws SQLException {

        StudentSubjectDTO studentSubjectDTO = new StudentSubjectDTO();

        List<Subject> subjectList = new ArrayList<>();

        try {
            connection = SingletonConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();

            StringBuilder sql = new StringBuilder("select s.*,st.name as sname from student as st\n" +
                    "INNER JOIN student_subject_relation ON student_subject_relation.student_id = st.id \n" +
                    "INNER JOIN subject as s ON s.id = student_subject_relation.subject_id \n" +
                    "WHERE st.id =");

            sql.append(studentId);

            ResultSet rs = statement.executeQuery(sql.toString());

            while (rs.next()) {
                Subject subject = new Subject();

                subject.setId(rs.getInt("id"));
                subject.setName(rs.getString("name"));
                subject.setCredits(rs.getInt("credits"));
                subject.setSemester(rs.getString("semester"));
                studentSubjectDTO.setStudentName(rs.getString("sname"));
                subjectList.add(subject);
            }
            studentSubjectDTO.setStudentId(studentId);
            studentSubjectDTO.setSubjectList(subjectList);
            studentSubjectDTO.setLengthOfList(subjectList.size());

        } catch (SQLException e) {
            System.out.println("error occured " + e.getMessage());
            throw e;
        }

        return studentSubjectDTO;
    }

    @Override
    public void saveStudentSubjectsIds(Integer studentId, Integer subjectId) throws SQLException {

        try {

            PreparedStatement statement1;
            String sql;

                sql= "INSERT INTO student_subject_relation (student_id,subject_id) VALUES (?, ?)";

                statement1 = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                statement1.setInt(1, studentId);
                statement1.setInt(2, subjectId);
                int affectedRows = statement1.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("error with subject id " + subjectId);
                }


        } catch (SQLException e) {
            System.out.println("error occured " + e.getMessage());
            throw e;

        }
    }

}
