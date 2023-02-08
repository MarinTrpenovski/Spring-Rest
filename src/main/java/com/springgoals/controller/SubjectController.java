package com.springgoals.controller;

import com.springgoals.exception.CustomException;
import com.springgoals.model.Subject;
import com.springgoals.service.impl.SubjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

import java.util.List;

@RestController
@RequestMapping("/api/subject")
public class SubjectController {

    @Autowired
    private SubjectServiceImpl subjectService;


    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Subject>> getSubjects() throws SQLException {

        List<Subject> subjects = subjectService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(subjects);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Subject> getById(@PathVariable("id") Integer id) throws SQLException {
        Subject subject = subjectService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(subject);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> add(@RequestBody Subject subject) throws SQLException, CustomException {

        subjectService.save(subject);
        return ResponseEntity.status(HttpStatus.CREATED).body("Successfully Created");
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> update(@RequestBody Subject subject) throws SQLException, CustomException {
        subjectService.update(subject);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully updated");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteCountry(@PathVariable("id") Integer id) throws SQLException {

        subjectService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully deleted");
    }

}
