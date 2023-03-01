package com.springgoals.service;

import com.springgoals.exception.ValidationsException;
import com.springgoals.model.Student;
import com.springgoals.model.dto.StudentSubjectDTO;
import com.springgoals.model.dto.StudentSubjectsOddDTO;
import com.springgoals.model.dto.UpdateStudentSubjectDTO;


import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface StudentService {

    Student getById(Integer id) throws SQLException;

    List<Student> getAll() throws SQLException;

    Map<Integer, Student> getMap() throws SQLException;

    List<Student> searchStudents(String name, String surname, String location, Integer indeks)
            throws SQLException;

    void update(Student student) throws SQLException, ValidationsException;

    void save(Student student) throws SQLException, ValidationsException;

    void delete(Integer id) throws SQLException;

    StudentSubjectDTO getSubjectsByStudId(Integer id) throws SQLException;

    void updateSubjectStudent(UpdateStudentSubjectDTO updateStudentSubjectDTO) throws ValidationsException, SQLException;

    void saveStudentSubjects(UpdateStudentSubjectDTO updateStudentSubjectDTO) throws ValidationsException, SQLException;

    void saveStudentSubjectsIds(Integer studentId, Integer subjectId) throws SQLException;

    void deleteStudentSubjects(Integer studentId, Integer [] subjectsIds ) throws SQLException;

     StudentSubjectsOddDTO getOddSubjectsByStudId(Integer id) throws SQLException;
}
