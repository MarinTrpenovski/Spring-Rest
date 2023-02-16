package com.springgoals.controller;

import com.springgoals.exception.QueryException;
import com.springgoals.exception.ValidationsException;
import com.springgoals.model.Student;
import com.springgoals.model.Subject;
import com.springgoals.model.dto.StudentSubjectDTO;
import com.springgoals.model.dto.UpdateStudentSubjectDTO;
import com.springgoals.service.impl.StudentServiceImpl;
import com.springgoals.service.impl.SubjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Student>> getStudents() throws SQLException {

        List<Student> students = studentService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(students);
    }

    @RequestMapping(value = "/map", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<Integer, Student>> mapStudents() throws SQLException {

        Map<Integer, Student> students = studentService.getMap();
        return ResponseEntity.status(HttpStatus.OK).body(students);
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Student>> searchStudents(
            @RequestParam("name") String name,
            @RequestParam("surname") String surname,
            @RequestParam("location") String location,
            @RequestParam("indeks") Integer indeks
    ) throws SQLException, QueryException {
        List<Student> students = null;
        if ((name == null || name.equals("")) && (surname == null || surname.equals("")) && (location == null || location.equals("")) && (indeks == null || indeks.equals(""))) {
            throw new QueryException("Error occurred: not enough query parameters");
        } else {
            students = studentService.searchStudents(name, surname, location, indeks);
        }
        return ResponseEntity.status(HttpStatus.OK).body(students);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> getById(@PathVariable("id") Integer id) throws SQLException {
        Student student = studentService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(student);
    }

    @RequestMapping(value = "/subjects/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudentSubjectDTO> getSubjectsByStudId(@PathVariable("id") Integer id)
            throws SQLException, ValidationsException {

        StudentSubjectDTO studentSubjectDTO;

        if (id == null || id == 0) {
            throw new ValidationsException("Error occurred:id can not be zero or null");
        } else {
            studentSubjectDTO = studentService.getSubjectsByStudId(id);
        }
        return ResponseEntity.status(HttpStatus.OK).body(studentSubjectDTO);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> add(@RequestBody Student student) throws SQLException, ValidationsException {

        studentService.save(student);
        return ResponseEntity.status(HttpStatus.CREATED).body("Successfully Created");
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> update(@RequestBody Student student) throws SQLException, ValidationsException {
        studentService.update(student);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully updated");
    }

    @RequestMapping(value = "/update/dto", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> update(@RequestBody  UpdateStudentSubjectDTO updateStudentSubjectDTO  ) throws SQLException, ValidationsException {

    if(updateStudentSubjectDTO.getStudent() != null || updateStudentSubjectDTO.getSubject() != null){

        studentService.updateSubjectStudent(updateStudentSubjectDTO);
    }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully updated");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteCountry(@PathVariable("id") Integer id) throws SQLException {

        studentService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully deleted");
    }

}
