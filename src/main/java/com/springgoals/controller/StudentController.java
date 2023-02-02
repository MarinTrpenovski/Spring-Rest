package com.springgoals.controller;

import com.springgoals.model.Student;
import com.springgoals.service.impl.ProfessorServiceImpl;
import com.springgoals.service.impl.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private StudentServiceImpl studentService;

    private static List<Student> students;

    private void init() {
        students = new ArrayList<>();
        students.add(new Student("Alek", "Aleksovski", 140040, "center"));

    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Student>> getStudents() throws SQLException {
        List<Student> students = studentService.getAll();
        init();
        return ResponseEntity.status(HttpStatus.OK).body(this.students);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> getById(@PathVariable("id") Integer id) throws SQLException {
        Student student = studentService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(student);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> add(@RequestBody Student student) throws SQLException {
        students.add(student);
        studentService.save(student);
        return ResponseEntity.status(HttpStatus.CREATED).body("Successfully Created");
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> update(@RequestBody Student student) throws SQLException {
        studentService.update(student);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully updated");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteCountry(@PathVariable("id") Integer id) throws SQLException {
        for (Student student : students) {
            if (student.getId() == id) {
                students.remove(student);
            }
        }
        studentService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully deleted");
    }

}
