package com.springgoals.controller;

import com.springgoals.exception.EntityNotFoundException;
import com.springgoals.exception.QueryException;
import com.springgoals.exception.ValidationsException;
import com.springgoals.model.Subject;
import com.springgoals.service.StudentService;
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
@RequestMapping("/api/subject")
public class SubjectController {

    @Autowired
    private SubjectServiceImpl subjectService;
    @Autowired
    private StudentService studentService;


    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Subject>> getSubjects() throws SQLException {

        List<Subject> subjects = subjectService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(subjects);
    }

    @RequestMapping(value = "/map", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<Integer, Subject>> mapSubject() throws SQLException {

        Map<Integer, Subject> subjects = subjectService.getMap();
        return ResponseEntity.status(HttpStatus.OK).body(subjects);
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Subject>> searchSubjects(
            @RequestParam("name") String name,
            @RequestParam("semester") String semester,
            @RequestParam("credits") Integer credits
    ) throws SQLException, QueryException {
        List<Subject> subjects = null;
        if ((name == null || name.equals("")) && (semester == null || semester.equals("")) && (credits == null || credits.equals(""))) {
            throw new QueryException("Error occurred: not enough query parameters");
        } else {
            subjects = subjectService.searchSubjects(name, semester, credits);
        }
        return ResponseEntity.status(HttpStatus.OK).body(subjects);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Subject> getById(@PathVariable("id") Integer id) throws SQLException {
        Subject subject = subjectService.getById(id);
        if (subject.getId() == null) {
            throw new EntityNotFoundException("Subject with id " + id + " not found in DB ");
        }
        return ResponseEntity.status(HttpStatus.OK).body(subject);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> add(@RequestBody Subject subject) throws SQLException, ValidationsException {

        if (subject == null) {
            throw new ValidationsException("Missing subject payload");
        }
        subjectService.save(subject);
        return ResponseEntity.status(HttpStatus.CREATED).body("Successfully Created");
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> update(@RequestBody Subject subject) throws SQLException, ValidationsException {

        if (subject == null) {
            throw new ValidationsException("Missing subject payload");
        }
        subjectService.update(subject);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully updated");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteSubject(@PathVariable("id") Integer id) throws SQLException {

        subjectService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully deleted");
    }

}
