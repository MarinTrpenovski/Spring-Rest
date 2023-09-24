package com.springgoals.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springgoals.exception.EntityNotFoundException;
import com.springgoals.exception.QueryException;
import com.springgoals.exception.ValidationsException;
import com.springgoals.model.Student;
import com.springgoals.model.dto.StudentSubjectDTO;
import com.springgoals.model.dto.StudentSubjectsOddDTO;
import com.springgoals.model.dto.UpdateStudentSubjectDTO;
import com.springgoals.service.impl.StudentServiceImpl;
import com.springgoals.service.impl.SubjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.sql.SQLException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private StudentServiceImpl studentService;

    @Autowired
    private SubjectServiceImpl subjectService;
    ObjectMapper objectMapper = new ObjectMapper();
    @RolesAllowed({"Admin", "User"})
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Student>> getStudents() throws SQLException {

        List<Student> students = studentService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(students);
    }
    @RolesAllowed({"Admin", "User"})
    @RequestMapping(value = "/map", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<Integer, Student>> mapStudents() throws SQLException {

        Map<Integer, Student> students = studentService.getMap();
        return ResponseEntity.status(HttpStatus.OK).body(students);
    }
    @RolesAllowed({"Admin", "User"})
    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Student>> searchStudents(
            @RequestParam("name") String name,
            @RequestParam("surname") String surname,
            @RequestParam("location") String location,
            @RequestParam("indeks") Integer indeks
    ) throws SQLException, QueryException {
        List<Student> students ;
        if ((name == null || name.equals("")) && (surname == null || surname.equals("")) && (location == null || location.equals("")) && (indeks == null || indeks.equals(""))) {
            throw new QueryException("Error occurred: not enough query parameters");
        } else {
            students = studentService.searchStudents(name, surname, location, indeks);
        }
        return ResponseEntity.status(HttpStatus.OK).body(students);
    }
    @RolesAllowed({"Admin", "User"})
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> getById(@PathVariable("id") Integer id) throws SQLException {

        Student student = studentService.getById(id);
        if (student.getId() == null) {
            throw new EntityNotFoundException("Student with id " + id + " not found in DB ");
        }
        return ResponseEntity.status(HttpStatus.OK).body(student);
    }

    @RolesAllowed({"Admin", "User"})
    @RequestMapping(value = "/subjects/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudentSubjectDTO> getSubjectsByStudId(@PathVariable("id") Integer id)
            throws SQLException, ValidationsException {

        StudentSubjectDTO studentSubjectDTO;

        if (id == null || id == 0) {
            throw new ValidationsException("Error occurred:studentId can not be zero or null");
        }
        Student student = studentService.getById(id);
        if (student == null) {
            throw new ValidationsException("student with id doesn't exist in db");
        }
        studentSubjectDTO = studentService.getSubjectsByStudId(id);

        return ResponseEntity.status(HttpStatus.OK).body(studentSubjectDTO);
    }

    @RolesAllowed({"Admin", "User"})
    @RequestMapping(value = "/odd/subjects/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudentSubjectsOddDTO> getOddSubjectsByStudId(@PathVariable("id") Integer id)
            throws SQLException, ValidationsException {

        StudentSubjectsOddDTO studentSubjectsOddDTO;

        if (id == null || id == 0) {
            throw new ValidationsException("Error occurred:studentId can not be zero or null");
        }
        Student student = studentService.getById(id);
        if (student == null) {
            throw new ValidationsException("Missing student payload");
        }
        studentSubjectsOddDTO = studentService.getOddSubjectsByStudId(id);

        return ResponseEntity.status(HttpStatus.OK).body(studentSubjectsOddDTO);
    }
    @Secured("Admin")
    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.ALL_VALUE)
    public ResponseEntity<String> add(@RequestBody Student student) throws SQLException, ValidationsException, JsonProcessingException {

        if (student == null) {
            throw new ValidationsException("Missing student payload");
        }
        studentService.save(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(objectMapper.writeValueAsString("Successfully Created"));
    }
    @Secured("Admin")
    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> update(@RequestBody Student student) throws SQLException, ValidationsException {

        if (student == null) {
            throw new ValidationsException("Missing student payload");
        }
        studentService.update(student);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully updated");
    }
    @Secured("Admin")
    @RequestMapping(value = "/save/student-subjects", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addStudentSubjects(@RequestBody UpdateStudentSubjectDTO updateStudentSubjectDTO) throws SQLException, ValidationsException {

        if (updateStudentSubjectDTO.getStudent().getId() == null || updateStudentSubjectDTO.getStudent().getId() == 0) {
            throw new ValidationsException("Error occurred:studentId can not be zero or null");
        } else if (updateStudentSubjectDTO.getSubjectList() == null || updateStudentSubjectDTO.getSubjectList().size() == 0) {
            throw new ValidationsException("Error in addStudentSubjects: SubjectList can not be zero or null");

        }
        studentService.saveStudentSubjects(updateStudentSubjectDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully created");
    }
    @Secured("Admin")
    @RequestMapping(value = "/update/student-subject", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateStudentSubject(@RequestBody UpdateStudentSubjectDTO updateStudentSubjectDTO) throws SQLException, ValidationsException {

        if (updateStudentSubjectDTO.getStudent() != null || updateStudentSubjectDTO.getSubject() != null) {
            studentService.updateSubjectStudent(updateStudentSubjectDTO);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully updated");
    }
    @Secured("Admin")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteStudent(@PathVariable("id") Integer id) throws SQLException {

        studentService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully deleted");
    }
    @Secured("Admin")
    @RequestMapping(value = "/delete/student-subjects", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteStudentSubjects(
            @RequestParam("id") Integer id,
            @RequestParam("subjectsIds") Integer[] subjectsIds)

            throws SQLException, ValidationsException {

        Student student = studentService.getById(id);
        if (student.getId() == null) {
            throw new EntityNotFoundException("Student with id " + id + " not found in DB ");
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully deleted");
    }
}
