package com.springgoals.controller;

import com.springgoals.model.University;
import com.springgoals.service.impl.SubjectServiceImpl;
import com.springgoals.service.impl.UniversityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/university")
public class UniversityController {

    @Autowired
    private UniversityServiceImpl universityService;


    private static List<University> universities;

    private void init() {
        universities = new ArrayList<>();
        universities.add(new University("Nova", "private"));

    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<University>> getUniversitys() throws SQLException {
        List<University> universities = universityService.getAll();
        init();
        return ResponseEntity.status(HttpStatus.OK).body(this.universities);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<University> getById(@PathVariable("id") Integer id) throws SQLException {
        University university = universityService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(university);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> add(@RequestBody University university) throws SQLException {
        universities.add(university);
        universityService.save(university);
        return ResponseEntity.status(HttpStatus.CREATED).body("Successfully Created");
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> update(@RequestBody University university) throws SQLException {
        universityService.update(university);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully updated");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteCountry(@PathVariable("id") Integer id) throws SQLException {
        for (University university : universities) {
            if (university.getId() == id) {
                universities.remove(university);
            }
        }
        universityService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully deleted");
    }

}
