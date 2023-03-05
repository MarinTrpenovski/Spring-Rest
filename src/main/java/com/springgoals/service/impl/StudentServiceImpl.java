package com.springgoals.service.impl;

import com.springgoals.dao.impl.StudentDAOImpl;
import com.springgoals.dao.impl.SubjectDAOImpl;
import com.springgoals.exception.ValidationsException;
import com.springgoals.model.Student;
import com.springgoals.model.Subject;
import com.springgoals.model.dto.StudentSubjectDTO;
import com.springgoals.model.dto.StudentSubjectsOddDTO;
import com.springgoals.model.dto.UpdateStudentSubjectDTO;
import com.springgoals.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

@Service

public class StudentServiceImpl implements StudentService {

    private Validator validator;

    @Autowired
    private StudentDAOImpl studentDAO;
    @Autowired
    private SubjectDAOImpl subjectDAO;

    @Override
    public Student getById(Integer id) throws SQLException {
        Student student = studentDAO.getById(id);
        return student;
    }

    @Override
    public List<Student> getAll() throws SQLException {
        return studentDAO.getAll();
    }

    @Override
    public Map<Integer, Student> getMap() throws SQLException {
        return studentDAO.getMap();
    }

    @Override
    public List<Student> searchStudents(String name, String surname, String location, Integer indeks)
            throws SQLException {
        StringBuilder sql = new StringBuilder("Select * from student where 1=1");
        if (name != null && !name.equals("")) {
            sql.append(" and name = \"");
            sql.append(name);
            sql.append("\"");
        }
        if (surname != null && !surname.equals("")) {
            sql.append(" and surname = \"");
            sql.append(surname);
            sql.append("\"");
        }
        if (location != null && !location.equals("")) {
            sql.append(" and location = \"");
            sql.append(location);
            sql.append("\"");
        }
        if (indeks != null && indeks != 0) {
            sql.append(" and indeks = ");
            sql.append(indeks);
        }
        return studentDAO.searchStudents(sql.toString());
    }

    @Override
    @Transactional
    public void update(Student student) throws SQLException, ValidationsException {
        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<Student> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }

            throw new ValidationsException("Error occurred: " + sb.toString());
        }

        studentDAO.update(student);
    }

    @Override
    @Transactional
    public void save(Student student) throws SQLException, ValidationsException {
        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<Student> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }

            throw new ValidationsException("Error occurred: " + sb.toString());
        }
        studentDAO.save(student);
    }


    @Override
    public StudentSubjectDTO getSubjectsByStudId(Integer id) throws SQLException {

        return studentDAO.getSubjectsByStudId(id);
    }

    @Override
    @Transactional
    public void updateSubjectStudent(UpdateStudentSubjectDTO updateStudentSubjectDTO) throws ValidationsException, SQLException {

        Set<ConstraintViolation<Student>> violationStudent = validator.validate(updateStudentSubjectDTO.getStudent());
        Set<ConstraintViolation<Subject>> violationSubject = validator.validate(updateStudentSubjectDTO.getSubject());

        if (!violationStudent.isEmpty() || !violationSubject.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<Student> constraintViolation : violationStudent) {
                sb.append(constraintViolation.getMessage());
            }
            for (ConstraintViolation<Subject> constraintViolation : violationSubject) {
                sb.append(constraintViolation.getMessage());
            }

            throw new ValidationsException("Error occurred: " + sb.toString());
        }

        studentDAO.update(updateStudentSubjectDTO.getStudent());
        subjectDAO.update(updateStudentSubjectDTO.getSubject());

    }

    @Override
    @Transactional
    public void saveStudentSubjects(UpdateStudentSubjectDTO updateStudentSubjectDTO) throws ValidationsException, SQLException {

        Set<ConstraintViolation<Student>> violationStudent = validator.validate(updateStudentSubjectDTO.getStudent());
        Set<ConstraintViolation<Subject>> violationSubject = null;

        for (Subject subject : updateStudentSubjectDTO.getSubjectList()) {
            violationSubject = validator.validate(subject);
        }


        if (!violationStudent.isEmpty() || !violationSubject.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<Student> constraintViolation : violationStudent) {
                sb.append(constraintViolation.getMessage());
            }
            for (ConstraintViolation<Subject> constraintViolation : violationSubject) {
                sb.append(constraintViolation.getMessage());
            }

            throw new ValidationsException("Error occurred: " + sb.toString());
        }

        Integer subjectId;
        Integer studentId = studentDAO.saveReturnId(updateStudentSubjectDTO.getStudent());
        for (Subject subject : updateStudentSubjectDTO.getSubjectList()) {

            subjectId = subjectDAO.saveReturnId(subject);

            saveStudentSubjectsIds(studentId, subjectId);
        }

    }

    @Override
    public void saveStudentSubjectsIds(Integer studentId, Integer subjectId) throws SQLException {

        studentDAO.saveStudentSubjectsIds(studentId, subjectId);

    }


    @Override
    @Transactional
    public void delete(Integer id) throws SQLException {
        studentDAO.delete(id);
    }

    @Override
    public void deleteStudentSubjects(Integer studentId, Integer[] subjectsIds) throws SQLException {

        Integer numberOfStudents;


        for (Integer subjectId : subjectsIds) {
            try {
                numberOfStudents = subjectDAO.getSubjectStudents(studentId, subjectId);
            } catch (SQLException sqlException) {
                System.out.println("Error in deleteStudentSubjects numberOfStudents,sqlException.message: " + sqlException.getMessage());
                throw sqlException;
            }

            try {
                subjectDAO.removeSubjectStudentRelation(studentId, subjectId);

            } catch (SQLException sqlException) {
                System.out.println("Error in deleteStudentSubjects removeSubjectStudentRelation ,sqlException.message: " + sqlException.getMessage());
                throw sqlException;
            }

            try {
                if (numberOfStudents == 1) {

                    subjectDAO.delete(subjectId);
                }
                System.out.println("deleteDTO,for,deleteStudentSubjects");
            } catch (SQLException sqlException) {
                System.out.println("Error in deleteStudentSubjects  subjectDAO.delete(subjectId) ,sqlException.message: " + sqlException.getMessage());
                throw sqlException;
            }

        }

        try {
            studentDAO.delete(studentId);

        } catch (SQLException sqlException) {
            System.out.println("Error in deleteStudentSubjects,sqlException.message: " + sqlException.getMessage());
            throw sqlException;
        }


    }

    public StudentSubjectsOddDTO getOddSubjectsByStudId(Integer id) throws SQLException {
        return studentDAO.getOddSubjectsByStudId(id);


    }
}
