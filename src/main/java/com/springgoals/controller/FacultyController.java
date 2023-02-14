package com.springgoals.controller;

import com.springgoals.exception.QueryException;
import com.springgoals.exception.ValidationsException;
import com.springgoals.model.Faculty;
import com.springgoals.service.impl.FacultyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/faculty")
public class FacultyController {

    @Autowired
    private FacultyServiceImpl facultyService;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Faculty>> getFaculties() throws SQLException {

        List<Faculty> faculties = facultyService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(faculties);
    }

    @RequestMapping(value = "/map", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<Integer, Faculty>> mapFaculties() throws SQLException {

        Map<Integer, Faculty> faculties = facultyService.getMap();
        return ResponseEntity.status(HttpStatus.OK).body(faculties);
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Faculty>> searchFaculties(
            @RequestParam("name") String name,
            @RequestParam("location") String location,
            @RequestParam("study_field") String study_field
    ) throws SQLException, QueryException {
        List<Faculty> faculties = null;
        if ((name == null || name.equals("")) && (location == null || location.equals("")) && (study_field == null || study_field.equals(""))) {
            throw new QueryException("Error occurred: not enough query parameters");
        } else {
            faculties = facultyService.searchFaculties(name, location, study_field);
        }
        return ResponseEntity.status(HttpStatus.OK).body(faculties);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Faculty> getById(@PathVariable("id") Integer id) throws SQLException {
        Faculty faculty = facultyService.getById(id);
        System.out.println("Test commit for ");
        return ResponseEntity.status(HttpStatus.OK).body(faculty);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> add(@RequestBody Faculty faculty) throws SQLException, ValidationsException {

        facultyService.save(faculty);
        return ResponseEntity.status(HttpStatus.CREATED).body("Successfully Created");
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> update(@RequestBody Faculty faculty) throws SQLException, ValidationsException {
        facultyService.update(faculty);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully updated");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteCountry(@PathVariable("id") Integer id) throws SQLException {

        facultyService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully deleted");
    }

}
