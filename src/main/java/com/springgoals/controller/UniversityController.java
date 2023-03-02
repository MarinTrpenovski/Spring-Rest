package com.springgoals.controller;

import com.springgoals.exception.QueryException;
import com.springgoals.exception.EntityNotFoundException;
import com.springgoals.exception.ValidationsException;
import com.springgoals.model.dto.UniversityFacultiesDTO;
import com.springgoals.model.dto.UniversityFacultyDTO;
import com.springgoals.model.University;
import com.springgoals.service.impl.UniversityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/university")
public class UniversityController {

    @Autowired
    private UniversityServiceImpl universityService;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<University>> getUniversitys() throws SQLException {

        List<University> universities = universityService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(universities);
    }

    @RequestMapping(value = "/map", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<Integer, University>> mapUniversities() throws SQLException {

        Map<Integer, University> universities = universityService.getMap();
        return ResponseEntity.status(HttpStatus.OK).body(universities);
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<University>> searchUniversities(
            @RequestParam("name") String name,
            @RequestParam("description") String description
    ) throws SQLException, QueryException {
        List<University> universities = null;
        if ((name == null || name.equals("")) && (description == null || description.equals(""))) {
            throw new QueryException("Error occurred: not enough query parameters");
        } else {
            universities = universityService.searchUniversities(name, description);
        }
        return ResponseEntity.status(HttpStatus.OK).body(universities);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<University> getById(@PathVariable("id") Integer id) throws SQLException {

        University university = universityService.getById(id);
        if (university.getId() == null) {
            throw new EntityNotFoundException("University with id " + id + " not found in DB ");
        }
        return ResponseEntity.status(HttpStatus.OK).body(university);
    }

    @RequestMapping(value = "/faculties/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UniversityFacultyDTO> getFacultiesByUniId(@PathVariable("id") Integer id)
            throws SQLException, ValidationsException {

        UniversityFacultyDTO universityFacultyDTO;

        if (id == null || id == 0) {
            throw new ValidationsException("Error occurred:id can not be zero or null");
        } else {
            universityFacultyDTO = universityService.getFacultiesByUniId(id);
        }
        return ResponseEntity.status(HttpStatus.OK).body(universityFacultyDTO);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> add(@RequestBody University university) throws SQLException, ValidationsException {

        if (university == null) {
            throw new ValidationsException("Missing university payload");}
        universityService.save(university);
        return ResponseEntity.status(HttpStatus.CREATED).body("Successfully Created");
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> update(@RequestBody University university) throws SQLException, ValidationsException {

        if (university == null) {
            throw new ValidationsException("Missing university payload");}
        universityService.update(university);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully updated");
    }

    @RequestMapping(value = "/save/university-faculties", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addUniversityFaculties (@RequestBody UniversityFacultiesDTO updateUniversityFacultiesDTO) throws SQLException, ValidationsException {

        if (updateUniversityFacultiesDTO.getUniversity() != null && (updateUniversityFacultiesDTO.getFacultyList() != null && updateUniversityFacultiesDTO.getFacultyList().size()>0)) {
            universityService.saveUniversityFaculties(updateUniversityFacultiesDTO);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully created");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteUniversity(@PathVariable("id") Integer id) throws SQLException {

        universityService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully deleted");
    }

}
