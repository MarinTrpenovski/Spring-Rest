package com.springgoals.controller;

import com.springgoals.exception.ValidationsException;
import com.springgoals.model.Professor;

import com.springgoals.service.impl.ProfessorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

import java.util.List;

@RestController
@RequestMapping("/api/professor")
public class ProfessorController {

    @Autowired
    private ProfessorServiceImpl professorService;


    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Professor>> getProfessors() throws SQLException {
        List<Professor> professors  = professorService.getAll();

        return ResponseEntity.status(HttpStatus.OK).body( professors);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Professor> getById(@PathVariable("id") Integer id) throws SQLException {
        Professor professor = professorService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(professor);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> add(@RequestBody Professor professor) throws SQLException, ValidationsException {

        professorService.save(professor);
        return ResponseEntity.status(HttpStatus.CREATED).body("Successfully Created");
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> update(@RequestBody Professor professor) throws SQLException, ValidationsException {
        professorService.update(professor);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully updated");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteCountry(@PathVariable("id") Integer id) throws SQLException {

        professorService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully deleted");
    }

}
