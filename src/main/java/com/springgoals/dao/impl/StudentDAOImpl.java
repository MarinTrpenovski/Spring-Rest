package com.springgoals.dao.impl;

import com.springgoals.dao.SingletonConnection;
import com.springgoals.dao.StudentDAO;
import com.springgoals.model.Student;
import com.springgoals.model.Subject;
import com.springgoals.model.dto.StudentSubjectDTO;
import com.springgoals.model.dto.StudentSubjectsOddDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository

public class StudentDAOImpl implements StudentDAO {

    private static final Logger logger = LogManager.getLogger(StudentDAOImpl.class);
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
                student.setImagePath(resultSet.getString("photo"));
            }
        } catch (SQLException e) {
            System.out.println("error occurred in StudentDAOImpl getById " + e.getMessage());
            logger.error("error occurred in StudentDAOImpl getById " + e.getMessage());
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
                student.setImagePath(rs.getString("photo"));
                studentList.add(student);
            }
        } catch (SQLException e) {
            System.out.println("error occurred in StudentDAOImpl getAll " + e.getMessage());
            logger.error("error occurred in StudentDAOImpl getAll " + e.getMessage());
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
            System.out.println("error occurred in StudentDAOImpl getMap " + e.getMessage());
            logger.error("error occurred in StudentDAOImpl getMap " + e.getMessage());
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
                student.setImagePath(rs.getString("photo"));

                studentList.add(student);
            }
        } catch (SQLException e) {
            System.out.println("error occurred in StudentDAOImpl searchStudents " + e.getMessage());
            logger.error("error occurred in StudentDAOImpl searchStudents " + e.getMessage());
            throw e;
        }
        return studentList;
    }

    @Override
    public void save(Student student) throws SQLException {

        try {
            String sql = "INSERT INTO student (name, surname, indeks, location, photo) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement1 = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement1.setString(1, student.getName());
            statement1.setString(2, student.getSurname());
            statement1.setInt(3, student.getIndeks());
            statement1.setString(4, student.getLocation());
            statement1.setString(5, student.getImagePath());

            int affectedRows = statement1.executeUpdate();

            if (affectedRows == 0) {
                logger.error("Error with affectedRows in StudentDAOImpl save");
                throw new SQLException("Error with affectedRows in StudentDAOImpl save");
            }

        } catch (SQLException e) {
            System.out.println("error occurred in StudentDAOImpl save " + e.getMessage());
            logger.error("error occurred in StudentDAOImpl save " + e.getMessage());
            throw e;
        }
    }

    @Override
    public Integer saveReturnId(Student student) throws SQLException {

        Integer id;

        try {
            String sql = "INSERT INTO student (name, surname, indeks, location, photo) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement1 = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement1.setString(1, student.getName());
            statement1.setString(2, student.getSurname());
            statement1.setInt(3, student.getIndeks());
            statement1.setString(4, student.getLocation());
            statement1.setString(5, student.getImagePath());


            int affectedRows = statement1.executeUpdate();

            if (affectedRows == 0) {
                logger.error("Error with affectedRows in StudentDAOImpl saveReturnId");
                throw new SQLException("Error with affectedRows in StudentDAOImpl saveReturnId");
            }

            try (ResultSet generatedKeys = statement1.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);
                } else {
                    logger.error("Error with generatedKeys in StudentDAOImpl saveReturnId");
                    throw new SQLException("Error with generatedKeys in StudentDAOImpl saveReturnId");
                }
            }
        } catch (SQLException e) {
            System.out.println("error occurred in StudentDAOImpl saveReturnId " + e.getMessage());
            logger.error("error occurred in StudentDAOImpl saveReturnId " + e.getMessage());
            throw e;
        }
        return id;
    }

    @Override
    public void update(Student student) throws SQLException {

        try {
            String sql = "UPDATE student SET name=?, surname=?, indeks=? ,location=?" +
                    ", photo=?  WHERE id=?";
            PreparedStatement statement1 = connection.prepareStatement(sql);
            statement1.setString(1, student.getName());
            statement1.setString(2, student.getSurname());
            statement1.setInt(3, student.getIndeks());
            statement1.setString(4, student.getLocation());
            statement1.setString(5, student.getImagePath());
            statement1.setInt(6, student.getId());

            int rowsUpdated = statement1.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing student was updated");
            }

        } catch (SQLException e) {
            System.out.println("error occurred in StudentDAOImpl update " + e.getMessage());
            logger.error("error occurred in StudentDAOImpl update " + e.getMessage());
            throw e;
        }
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
            System.out.println("error occurred in StudentDAOImpl delete " + e.getMessage());
            logger.error("error occurred in StudentDAOImpl delete " + e.getMessage());
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
            System.out.println("error in StudentDAOImpl getSubjectsByStudId " + e.getMessage());
            logger.error("error in StudentDAOImpl getSubjectsByStudId " + e.getMessage());
            throw e;
        }

        return studentSubjectDTO;
    }

    @Override
    public StudentSubjectsOddDTO getOddSubjectsByStudId(Integer studentId) throws SQLException {

        Integer sumOfOddCredits = 0;

        StudentSubjectsOddDTO studentSubjectsOddDTO = new StudentSubjectsOddDTO();

        List<Subject> subjectList = new ArrayList<>();

        try {
            connection = SingletonConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();

            StringBuilder sql = new StringBuilder(
                    "select subj.*, st.name as studentName, st.indeks\n" +
                            "from student as st\n" +
                            "INNER JOIN student_subject_relation ON student_subject_relation.student_id = st.id \n" +
                            "INNER JOIN subject as subj ON subj.id = student_subject_relation.subject_id \n" +
                            "WHERE st.id =");

            sql.append(studentId);

            ResultSet rs = statement.executeQuery(sql.toString());

            while (rs.next()) {
                Subject subject = new Subject();

                subject.setId(rs.getInt("id"));
                subject.setName(rs.getString("name"));
                subject.setCredits(rs.getInt("credits"));
                subject.setSemester(rs.getString("semester"));
                studentSubjectsOddDTO.setStudentName(rs.getString("studentName"));
                studentSubjectsOddDTO.setIndeks(rs.getInt("indeks"));

                if (subject.getCredits() % 2 == 1) {
                    subjectList.add(subject);
                    sumOfOddCredits += subject.getCredits();

                }
            }

            studentSubjectsOddDTO.setStudentId(studentId);
            studentSubjectsOddDTO.setSubjectList(subjectList);
            studentSubjectsOddDTO.setLengthOfList(subjectList.size());
            studentSubjectsOddDTO.setSumOfCredits(sumOfOddCredits);

        } catch (SQLException e) {
            System.out.println("error in StudentDAOImpl getOddSubjectsByStudId " + e.getMessage());
            logger.error("error in StudentDAOImpl getOddSubjectsByStudId " + e.getMessage());
            throw e;
        }

        return studentSubjectsOddDTO;
    }

    @Override
    public void saveStudentSubjectsIds(Integer studentId, Integer subjectId) throws SQLException {

        try {

            PreparedStatement statement1;
            String sql = "INSERT INTO student_subject_relation (student_id,subject_id) VALUES (?, ?)";

            statement1 = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement1.setInt(1, studentId);
            statement1.setInt(2, subjectId);
            int affectedRows = statement1.executeUpdate();
            if (affectedRows == 0) {
                logger.error("Error with affectedRows in StudentDAOImpl saveStudentSubjectsIds");
                throw new SQLException("Error with affectedRows in StudentDAOImpl saveStudentSubjectsIds");
            }

        } catch (SQLException e) {
            System.out.println("error in StudentDAOImpl saveStudentSubjectsIds " + e.getMessage());
            logger.error("error in StudentDAOImpl saveStudentSubjectsIds " + e.getMessage());
            throw e;

        }
    }

    @Override
    public void deleteImages() throws SQLException {
        try {
            String sql = "INSERT INTO student ( photo ) VALUES (?)";
            PreparedStatement statement1 = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement1.setString(1, null);

            int affectedRows = statement1.executeUpdate();

            if (affectedRows == 0) {
                logger.error("Error with affectedRows in StudentDAOImpl deleteImages");
                throw new SQLException("Error with affectedRows in StudentDAOImpl deleteImages");
            }

        } catch (SQLException e) {
            System.out.println("error SQLException in StudentDAOImpl deleteImages " + e.getMessage());
            logger.error("error SQLException in StudentDAOImpl deleteImages " + e.getMessage());
            throw e;
        }
    }
}
