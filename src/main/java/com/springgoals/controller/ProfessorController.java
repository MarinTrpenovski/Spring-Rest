package com.springgoals.controller;

import com.springgoals.model.Professor;
import com.springgoals.service.impl.ProfessorServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/professor")
public class ProfessorController {


    private ProfessorServiceImpl professorService = new ProfessorServiceImpl();

    private static List<Professor> professors;

    private void init() {
        professors = new ArrayList<>();
        professors.add(new Professor("Mirko", "Marinovski ", "sp", "aps", 26));

    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Professor>> getProfessors() throws SQLException {
        List<Professor> professors = professorService.getAll();
        init();
        return ResponseEntity.status(HttpStatus.OK).body(this.professors);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Professor> getById(@PathVariable("id") Integer id) throws SQLException {
        Professor professor = professorService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(professor);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> add(@RequestBody Professor professor) throws SQLException {
        professors.add(professor);
        professorService.save(professor);
        return ResponseEntity.status(HttpStatus.CREATED).body("Successfully Created");
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> update(@RequestBody Professor professor) throws SQLException {
        professorService.update(professor);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully updated");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteCountry(@PathVariable("id") Integer id) throws SQLException {
        for (Professor professor : professors) {
            if (professor.getId() == id) {
                professors.remove(professor);
            }
        }
        professorService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully deleted");
    }

}
