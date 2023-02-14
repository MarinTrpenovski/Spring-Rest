package com.springgoals.controller;

import com.springgoals.exception.QueryException;
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
        List<Professor> professors = professorService.getAll();

        return ResponseEntity.status(HttpStatus.OK).body(professors);
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Professor>> searchProfessors(
            @RequestParam("name") String name,
            @RequestParam("surname") String surname,
            @RequestParam("age") Integer age,
            @RequestParam("primary_subject1") String primary_subject1,
            @RequestParam("primary_subject2") String primary_subject2
    ) throws SQLException, QueryException {
        List<Professor> professors = null;
        if (name == null && surname == null && age == null && primary_subject1 == null && (primary_subject2 == null || primary_subject2.equals("")) ) {
            throw new QueryException("Error occurred: not enough query parameters");
        } else {
            professors = professorService.searchProfessors(name, surname, age, primary_subject1, primary_subject2);
        }
        return ResponseEntity.status(HttpStatus.OK).body(professors);
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
