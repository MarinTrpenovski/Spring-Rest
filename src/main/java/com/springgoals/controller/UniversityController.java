package com.springgoals.controller;

import com.springgoals.model.dto.UniversityFacultiesDTO;
import com.springgoals.model.dto.UniversityFacultyDTO;
import com.springgoals.model.University;
import com.springgoals.service.impl.UniversityServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import com.springgoals.exception.QueryException;
import com.springgoals.exception.EntityNotFoundException;
import com.springgoals.exception.ValidationsException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/university")
public class UniversityController {

    private static final Logger logger = LogManager.getLogger(UniversityController.class);
    @Autowired
    private UniversityServiceImpl universityService;
    ObjectMapper objectMapper = new ObjectMapper();

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<University>> getUniversitys() throws SQLException {

        List<University> universities = universityService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(universities);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/map", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<Integer, University>> mapUniversities() throws SQLException {

        Map<Integer, University> universities = universityService.getMap();
        return ResponseEntity.status(HttpStatus.OK).body(universities);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<University>> searchUniversities(
            @RequestParam("name") String name,
            @RequestParam("description") String description
    ) throws SQLException, QueryException {
        List<University> universities = null;
        if ((name == null || name.equals("")) && (description == null || description.equals(""))) {
            logger.error("Error in searchUniversities: not enough query parameters");
            throw new QueryException("Error in searchUniversities: not enough query parameters");
        } else {
            universities = universityService.searchUniversities(name, description);
        }
        return ResponseEntity.status(HttpStatus.OK).body(universities);
    }

    @PreAuthorize("hasAuthority('EDIT')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<University> getById(@PathVariable("id") Integer id) throws SQLException {

        University university = universityService.getById(id);
        if (university.getId() == null) {
            logger.error("University with id " + id + " not found in DB ");
            throw new EntityNotFoundException("University with id " + id + " not found in DB ");
        }
        return ResponseEntity.status(HttpStatus.OK).body(university);
    }

    @PreAuthorize("hasAuthority('VIEW')")
    @RequestMapping(value = "/faculties/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UniversityFacultyDTO> getFacultiesByUniId(@PathVariable("id") Integer id)
            throws SQLException, ValidationsException {

        UniversityFacultyDTO universityFacultyDTO;

        if (id == null || id == 0) {
            logger.error("Error in getFacultiesByUniId: universityId can not be zero or null");
            throw new ValidationsException("Error  in getFacultiesByUniId: universityId can not be zero or null");
        } else {
            universityFacultyDTO = universityService.getFacultiesByUniId(id);
        }
        return ResponseEntity.status(HttpStatus.OK).body(universityFacultyDTO);
    }

    @PreAuthorize("hasAuthority('CREATE')")
    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> add(@RequestBody University university)
            throws SQLException, ValidationsException, JsonProcessingException {
        if (university == null) {
            logger.error("Missing university payload");
            throw new ValidationsException("Missing university payload");
        }
        universityService.save(university);
        return ResponseEntity.status(HttpStatus.CREATED).
                body(objectMapper.writeValueAsString("Successfully Created"));
    }

    @PreAuthorize("hasAuthority('UPDATE')")
    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> update(@RequestBody University university)
            throws SQLException, ValidationsException {
        if (university == null) {
            logger.error("Missing university payload");
            throw new ValidationsException("Missing university payload");
        }
        universityService.update(university);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully updated");
    }

    @PreAuthorize("hasAuthority('CREATE')")
    @RequestMapping(value = "/save/university-faculties", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addUniversityFaculties (@RequestBody UniversityFacultiesDTO updateUniversityFacultiesDTO) throws SQLException, ValidationsException {

        if (updateUniversityFacultiesDTO.getUniversity() != null && (updateUniversityFacultiesDTO.getFacultyList() != null && updateUniversityFacultiesDTO.getFacultyList().size()>0)) {
            universityService.saveUniversityFaculties(updateUniversityFacultiesDTO);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully created");
    }

    @PreAuthorize("hasAuthority('DELETE')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteUniversity(@PathVariable("id") Integer id)
            throws SQLException {

        universityService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully deleted");
    }

}
