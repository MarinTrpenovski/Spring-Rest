package com.springgoals.controller;

import com.springgoals.model.Faculty;
import com.springgoals.model.FileInfo;
import com.springgoals.model.University;
import com.springgoals.model.dto.FacultySubjectDTO;
import com.springgoals.service.impl.FacultyServiceImpl;
import com.springgoals.service.impl.FileServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import com.springgoals.exception.EntityNotFoundException;
import com.springgoals.exception.QueryException;
import com.springgoals.exception.ValidationsException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/faculty")
public class FacultyController {

    private static final Logger logger = LogManager.getLogger(FacultyController.class);
    @Autowired
    private FacultyServiceImpl facultyService;
    @Autowired
    private FileServiceImpl fileServiceImpl;
    ObjectMapper objectMapper = new ObjectMapper();

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Faculty>> getFaculties() throws SQLException {

        List<Faculty> faculties = facultyService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(faculties);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/map", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<Integer, Faculty>> mapFaculties() throws SQLException {

        Map<Integer, Faculty> faculties = facultyService.getMap();
        return ResponseEntity.status(HttpStatus.OK).body(faculties);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Faculty>> searchFaculties(
            @RequestParam("name") String name,
            @RequestParam("location") String location,
            @RequestParam("study_field") String study_field
    ) throws SQLException, QueryException {
        List<Faculty> faculties ;
        if ((name == null || name.equals("")) && (location == null || location.equals("")) && (study_field == null || study_field.equals(""))) {
            logger.error("Error in searchFaculties: not enough query parameters");
            throw new QueryException("Error in searchFaculties: not enough query parameters");
        } else {
            faculties = facultyService.searchFaculties(name, location, study_field);
        }
        return ResponseEntity.status(HttpStatus.OK).body(faculties);
    }

    @PreAuthorize("hasAuthority('EDIT')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Faculty> getById(@PathVariable("id") Integer id) throws SQLException {
        Faculty faculty = facultyService.getById(id);
        if (faculty.getId() == null) {
            logger.error("Faculty with id" + id + " not found in DB");
            throw new EntityNotFoundException("Faculty with id " + id + " not found in DB");
        }
        return ResponseEntity.status(HttpStatus.OK).body(faculty);
    }

    @PreAuthorize("hasAuthority('VIEW')")
    @RequestMapping(value = "/subjects/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FacultySubjectDTO> getSubjectsByFacId(@PathVariable("id") Integer id)
            throws SQLException, ValidationsException {

        FacultySubjectDTO facultySubjectDTO;
        if (id == null || id == 0) {
            logger.error("Error in getSubjectsByFacId:id can not be zero or null");
            throw new ValidationsException("Error in getSubjectsByFacId:id can not be zero or null");
        } else {
            facultySubjectDTO = facultyService.getSubjectsByFacId(id);
        }
        return ResponseEntity.status(HttpStatus.OK).body(facultySubjectDTO);
    }

    @PreAuthorize("hasAuthority('CREATE')")
    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> add(@RequestBody Faculty faculty) throws SQLException, ValidationsException, JsonProcessingException {

        if (faculty == null) {
            logger.error("Missing faculty payload");
            throw new ValidationsException("Missing faculty payload");
        }
        facultyService.save(faculty);
        return ResponseEntity.status(HttpStatus.CREATED).body(objectMapper.writeValueAsString("Successfully created new faculty"));
    }

    @PreAuthorize("hasAuthority('CREATE')")
    @RequestMapping(value = "/save-img", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addImg(Faculty faculty,
            @RequestParam("image") MultipartFile multipartFile
    ) throws SQLException, ValidationsException, IOException {
        if(faculty == null){
            logger.error("Missing faculty payload");
            throw new ValidationsException("Missing faculty payload");
        }
        if(multipartFile == null) {
            logger.error("Missing faculty picture");
            throw new ValidationsException("Missing faculty picture");
        }
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        fileServiceImpl.saveFile(fileName, multipartFile, "/faculty/");
        //facultyService.save(faculty);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                objectMapper.writeValueAsString("Successfully created new faculty with image"));
    }

    @PreAuthorize("hasAuthority('VIEW')")
    @RequestMapping(value = "/img/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Resource> getImageById(@PathVariable("id") Integer id) throws SQLException, IOException {
        Faculty faculty = facultyService.getById(id);
        if (faculty.getId() == null) {
            logger.error("Faculty with id" + id + " not found in DB");
            throw new EntityNotFoundException("Faculty with id " + id + " not found in DB");
        }
        Path imagePath = Paths.get(fileServiceImpl.uploadDirectory, "/faculty/" + faculty.getImagePath());
        Resource resource = new FileSystemResource(imagePath.toFile());
        String contentType = Files.probeContentType(imagePath);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType(contentType)).body(resource);
    }

    @PreAuthorize("hasAuthority('VIEW')")
    @GetMapping("/faculty/images")
    public ResponseEntity<List<FileInfo>> getListFiles() {
        List<FileInfo> fileInfos = fileServiceImpl.loadAll("/faculty/").map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(FacultyController.class, "getImageById", path.getFileName().toString()).build().toString();

            return new FileInfo(filename, url);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }


    @PreAuthorize("hasAuthority('UPDATE')")
    @RequestMapping(value = "/update-img", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateImg( Faculty faculty,
            @RequestParam("image") MultipartFile multipartFile) throws SQLException, ValidationsException, IOException {
        if(faculty == null){
            logger.error("Missing faculty payload");
            throw new ValidationsException("Missing faculty payload");
        }
        if(multipartFile == null) {
            logger.error("Missing faculty picture");
            throw new ValidationsException("Missing faculty picture");
        }
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        fileServiceImpl.saveFile(fileName, multipartFile, "/faculty/");
        //facultyService.update(faculty);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully added image for the faculty");
    }

    @PreAuthorize("hasAuthority('UPDATE')")
    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> update(@RequestBody Faculty faculty) throws SQLException, ValidationsException {

        if (faculty == null) {
            logger.error("Missing faculty payload");
            throw new ValidationsException("Missing faculty payload");
        }
        facultyService.update(faculty);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully updated");
    }

    @PreAuthorize("hasAuthority('DELETE')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteFaculty(@PathVariable("id") Integer id) throws SQLException {

        facultyService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully deleted");
    }

    @PreAuthorize("hasAuthority('DELETE')")
    @RequestMapping(value = "/delete/images", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteImagesFaculty()
            throws SQLException {
        fileServiceImpl.deleteAll("/faculty/");
        facultyService.deleteImages();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully deleted all images of faculties");
    }

}
