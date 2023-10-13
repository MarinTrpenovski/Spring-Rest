package com.springgoals.controller;

import com.springgoals.model.Student;
import com.springgoals.model.dto.StudentSubjectDTO;
import com.springgoals.model.dto.StudentSubjectsOddDTO;
import com.springgoals.model.dto.UpdateStudentSubjectDTO;
import com.springgoals.service.impl.StudentServiceImpl;
import com.springgoals.service.impl.SubjectServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import com.springgoals.exception.EntityNotFoundException;
import com.springgoals.exception.QueryException;
import com.springgoals.exception.ValidationsException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/student")
public class StudentController {

    private static final Logger logger = LogManager.getLogger(LogController.class);
    @Autowired
    private StudentServiceImpl studentService;
    @Autowired
    private SubjectServiceImpl subjectService;
    ObjectMapper objectMapper = new ObjectMapper();

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Student>> getStudents() throws SQLException {

        List<Student> students = studentService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(students);
    }
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/map", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<Integer, Student>> mapStudents() throws SQLException {

        Map<Integer, Student> students = studentService.getMap();
        return ResponseEntity.status(HttpStatus.OK).body(students);
    }
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Student>> searchStudents(
            @RequestParam("name") String name,
            @RequestParam("surname") String surname,
            @RequestParam("location") String location,
            @RequestParam("indeks") Integer indeks
    ) throws SQLException, QueryException {
        List<Student> students ;
        if ((name == null || name.equals("")) && (surname == null || surname.equals("")) && (location == null || location.equals("")) && (indeks == null || indeks.equals(""))) {
            logger.error("Error in searchStudents: not enough query parameters");
            throw new QueryException("Error in searchStudents: not enough query parameters");
        } else {
            students = studentService.searchStudents(name, surname, location, indeks);
        }
        return ResponseEntity.status(HttpStatus.OK).body(students);
    }

    @PreAuthorize("hasAuthority('EDIT')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> getById(@PathVariable("id") Integer id) throws SQLException {

        Student student = studentService.getById(id);
        if (student.getId() == null) {
            logger.error("Student with id " + id + " not found in DB ");
            throw new EntityNotFoundException("Student with id " + id + " not found in DB ");
        }
        return ResponseEntity.status(HttpStatus.OK).body(student);
    }

    @PreAuthorize("hasAuthority('VIEW')")
    @RequestMapping(value = "/subjects/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudentSubjectDTO> getSubjectsByStudId(@PathVariable("id") Integer id)
            throws SQLException, ValidationsException {

        StudentSubjectDTO studentSubjectDTO;

        if (id == null || id == 0) {
            logger.error("Error in getSubjectsByStudId: studentId can not be zero or null");
            throw new ValidationsException("Error in getSubjectsByStudId: studentId can not be zero or null");
        }
        Student student = studentService.getById(id);
        if (student == null) {
            logger.error("student with id doesn't exist in db");
            throw new ValidationsException("student with id doesn't exist in db");
        }
        studentSubjectDTO = studentService.getSubjectsByStudId(id);

        return ResponseEntity.status(HttpStatus.OK).body(studentSubjectDTO);
    }

    @PreAuthorize("hasAuthority('VIEW')")
    @RequestMapping(value = "/odd/subjects/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudentSubjectsOddDTO> getOddSubjectsByStudId(@PathVariable("id") Integer id)
            throws SQLException, ValidationsException {

        StudentSubjectsOddDTO studentSubjectsOddDTO;

        if (id == null || id == 0) {
            logger.error("Error in getOddSubjectsByStudId: studentId can not be zero or null");
            throw new ValidationsException("Error in getOddSubjectsByStudId: studentId can not be zero or null");
        }
        Student student = studentService.getById(id);
        if (student == null) {
            logger.error("Missing student payload");
            throw new ValidationsException("Missing student payload");
        }
        studentSubjectsOddDTO = studentService.getOddSubjectsByStudId(id);

        return ResponseEntity.status(HttpStatus.OK).body(studentSubjectsOddDTO);
    }

    @PreAuthorize("hasAuthority('CREATE')")
    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.ALL_VALUE)
    public ResponseEntity<String> add(@RequestBody Student student) throws SQLException, ValidationsException, JsonProcessingException {

        if (student == null) {
            logger.error("Missing student payload");
            throw new ValidationsException("Missing student payload");
        }
        studentService.save(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(objectMapper.writeValueAsString("Successfully Created"));
    }

    @PreAuthorize("hasAuthority('UPDATE')")
    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> update(@RequestBody Student student) throws SQLException, ValidationsException {

        if (student == null) {
            logger.error("Missing student payload");
            throw new ValidationsException("Missing student payload");
        }
        studentService.update(student);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully updated");
    }

    @PreAuthorize("hasAuthority('CREATE')")
    @RequestMapping(value = "/save/student-subjects", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addStudentSubjects(@RequestBody UpdateStudentSubjectDTO updateStudentSubjectDTO) throws SQLException, ValidationsException {

        if (updateStudentSubjectDTO.getStudent().getId() == null || updateStudentSubjectDTO.getStudent().getId() == 0) {
            logger.error("Error in addStudentSubjects: studentId can not be zero or null");
            throw new ValidationsException("Error in addStudentSubjects: studentId can not be zero or null");
        } else if (updateStudentSubjectDTO.getSubjectList() == null || updateStudentSubjectDTO.getSubjectList().size() == 0) {
            logger.error("Error in addStudentSubjects: SubjectList can not be zero or null");
            throw new ValidationsException("Error in addStudentSubjects: SubjectList can not be zero or null");
        }
        studentService.saveStudentSubjects(updateStudentSubjectDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully created");
    }

    @PreAuthorize("hasAuthority('UPDATE')")
    @RequestMapping(value = "/update/student-subject", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateStudentSubject(@RequestBody UpdateStudentSubjectDTO updateStudentSubjectDTO) throws SQLException, ValidationsException {

        if (updateStudentSubjectDTO.getStudent() != null || updateStudentSubjectDTO.getSubject() != null) {
            studentService.updateSubjectStudent(updateStudentSubjectDTO);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully updated student subject");
    }

    @PreAuthorize("hasAuthority('DELETE')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteStudent(@PathVariable("id") Integer id) throws SQLException {

        studentService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully deleted");
    }
    @PreAuthorize("hasAuthority('DELETE')")
    @RequestMapping(value = "/delete/student-subjects", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteStudentSubjects(
            @RequestParam("id") Integer id,
            @RequestParam("subjectsIds") Integer[] subjectsIds) throws SQLException {

        Student student = studentService.getById(id);
        if (student.getId() == null) {
            logger.error("Error in deleteStudentSubjects: student with id " + id + " not found in DB ");
            throw new EntityNotFoundException("Error in deleteStudentSubjects: student with id " + id + " not found in DB ");
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully deleted");
    }
}
