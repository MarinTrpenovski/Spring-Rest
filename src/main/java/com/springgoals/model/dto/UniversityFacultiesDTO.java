package com.springgoals.model.dto;

import com.springgoals.model.Faculty;
import com.springgoals.model.University;

import java.util.List;
import java.util.Objects;

public class UniversityFacultiesDTO {

    private University university;

    List<Faculty> facultyList;


    public UniversityFacultiesDTO () {}

    public UniversityFacultiesDTO(University university, List<Faculty> facultyList) {
        this.university = university;
        this.facultyList = facultyList;
    }

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    public List<Faculty> getFacultyList() {
        return facultyList;
    }

    public void setFacultyList(List<Faculty> facultyList) {
        this.facultyList = facultyList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UniversityFacultiesDTO that = (UniversityFacultiesDTO) o;
        return Objects.equals(university, that.university) && Objects.equals(facultyList, that.facultyList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(university, facultyList);
    }
}
