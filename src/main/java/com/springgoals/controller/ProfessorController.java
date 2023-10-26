package com.springgoals.controller;

import com.springgoals.model.FileInfo;
import com.springgoals.model.Professor;
import com.springgoals.model.University;
import com.springgoals.service.impl.FileServiceImpl;
import com.springgoals.service.impl.ProfessorServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
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
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/professor")
public class ProfessorController {

    private static final Logger logger = LogManager.getLogger(ProfessorController.class);
    @Autowired
    private ProfessorServiceImpl professorService;

    private FileServiceImpl fileServiceImpl;

    ObjectMapper objectMapper = new ObjectMapper();

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Professor>> getProfessors() throws SQLException {
        List<Professor> professors = professorService.getAll();

        return ResponseEntity.status(HttpStatus.OK).body(professors);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/map", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<Integer, Professor>> mapProfessors() throws SQLException {

        Map<Integer, Professor> professors = professorService.getMap();
        return ResponseEntity.status(HttpStatus.OK).body(professors);
    }
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Professor>> searchProfessors(
            @RequestParam("name") String name,
            @RequestParam("surname") String surname,
            @RequestParam("age") Integer age,
            @RequestParam("primary_subject1") String primary_subject1,
            @RequestParam("primary_subject2") String primary_subject2
    ) throws SQLException, QueryException {
        List<Professor> professors = null;
        if ((name == null || name.equals("")) && (surname == null || surname.equals("")) &&
                (age == null || age.equals("")) && (primary_subject1 == null || primary_subject1.equals(""))
                && (primary_subject2 == null || primary_subject2.equals(""))) {
            logger.error("Error in searchProfessors: not enough query parameters");
            throw new QueryException("Error in searchProfessors: not enough query parameters");
        } else {
            professors = professorService.searchProfessors(name, surname, age, primary_subject1, primary_subject2);
        }
        return ResponseEntity.status(HttpStatus.OK).body(professors);
    }

    @PreAuthorize("hasAuthority('EDIT')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Professor> getById(@PathVariable("id") Integer id) throws SQLException {
        Professor professor = professorService.getById(id);
        if (professor.getId() == null) {
            logger.error("Professor with id " + id + " not found in DB ");
            throw new EntityNotFoundException("Professor with id " + id + " not found in DB ");
        }
        return ResponseEntity.status(HttpStatus.OK).body(professor);
    }

    @PreAuthorize("hasAuthority('CREATE')")
    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> add(@RequestBody Professor professor) throws SQLException, ValidationsException, JsonProcessingException {

        if (professor == null) {
            logger.error("Missing professor payload");
            throw new ValidationsException("Missing professor payload");
        }
        professorService.save(professor);
        return ResponseEntity.status(HttpStatus.CREATED).body(objectMapper.writeValueAsString("Successfully Created"));
    }

    @PreAuthorize("hasAuthority('CREATE')")
    @RequestMapping(value = "/save-img", method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addImg( Professor professor,
                                     @RequestParam("image") MultipartFile multipartFile
    ) throws SQLException, ValidationsException, IOException {
        if (professor == null) {
            logger.error("Missing professor payload");
            throw new ValidationsException("Missing professor payload");
        }
        if (multipartFile == null) {
            logger.error("Missing professor picture");
            throw new ValidationsException("Missing professor picture");
        }
        String fileName = multipartFile.getOriginalFilename();
        Path filePathAndName = Paths.get(fileServiceImpl.uploadDirectory, "/professor/" + fileName);

        Files.write(filePathAndName, multipartFile.getBytes());
        professor.setImagePath(fileName);
        // professorService.save(professor);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('VIEW')")
    @RequestMapping(value = "/img/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Resource> getImageById(@PathVariable("id") Integer id) throws SQLException, IOException {

        Professor professor = professorService.getById(id);
        if (professor.getId() == null) {
            logger.error("Professor with id " + id + " not found in DB ");
            throw new EntityNotFoundException("Professor with id " + id + " not found in DB ");
        }
        Path imagePath = Paths.get(fileServiceImpl.uploadDirectory, "/professor/" + professor.getImagePath());
        Resource resource = new FileSystemResource(imagePath.toFile());
        String contentType = Files.probeContentType(imagePath);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType(contentType)).body(resource);
    }

    @PreAuthorize("hasAuthority('VIEW')")
    @GetMapping("/professor/images")
    public ResponseEntity<List<FileInfo>> getListFiles() {
        List<FileInfo> fileInfos = fileServiceImpl.loadAll("/professor/").map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(ProfessorController.class, "getImageById", path.getFileName().toString()).build().toString();

            return new FileInfo(filename, url);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

    @PreAuthorize("hasAuthority('UPDATE')")
    @RequestMapping(value = "/update-img", method = RequestMethod.PUT,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateImg( Professor professor,
                                             @RequestParam("image") MultipartFile multipartFile
    ) throws SQLException, ValidationsException, IOException {
        if (professor == null) {
            logger.error("Missing professor payload");
            throw new ValidationsException("Missing professor payload");
        }
        if (multipartFile == null) {
            logger.error("Missing professor picture");
            throw new ValidationsException("Missing professor picture");
        }
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        fileServiceImpl.saveFile(fileName, multipartFile, "/professor/");
        professor.setImagePath(fileName);
        //professorService.update(professor);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully added image for professor");
    }

    @PreAuthorize("hasAuthority('UPDATE')")
    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> update(@RequestBody Professor professor) throws SQLException, ValidationsException {

        if (professor == null) {
            logger.error("Missing professor payload");
            throw new ValidationsException("Missing professor payload");
        }
        professorService.update(professor);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully updated");
    }

    @PreAuthorize("hasAuthority('DELETE')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteProfessor(@PathVariable("id") Integer id) throws SQLException {

        professorService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully deleted");
    }

    @PreAuthorize("hasAuthority('DELETE')")
    @RequestMapping(value = "/delete/images", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteImagesProfessor()
            throws SQLException {
        fileServiceImpl.deleteAll("/professor/");
        professorService.deleteImages();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully deleted all images of professors");
    }
}
