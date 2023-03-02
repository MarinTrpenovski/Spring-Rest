package com.springgoals.model.dto;

import com.springgoals.model.Faculty;
import com.springgoals.model.University;

import java.util.List;
import java.util.Objects;

public class UniversityFacultiesDTO {

    private University university;


    private List<Faculty> facultyList;


    private Integer lengthOfList;


    public UniversityFacultiesDTO () {}

    public UniversityFacultiesDTO(University university, List<Faculty> facultyList) {
        this.university = university;
        this.facultyList = facultyList;
    }

    public UniversityFacultiesDTO(University university, List<Faculty> facultyList, Integer lengthOfList) {
        this.university = university;
        this.facultyList = facultyList;
        this.lengthOfList = lengthOfList;
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

    public Integer getLengthOfList() {
        return lengthOfList;
    }

    public void setLengthOfList(Integer lengthOfList) {
        this.lengthOfList = lengthOfList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UniversityFacultiesDTO that = (UniversityFacultiesDTO) o;
        return Objects.equals(university, that.university) && Objects.equals(facultyList, that.facultyList) && Objects.equals(lengthOfList, that.lengthOfList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(university, facultyList, lengthOfList);
    }

    @Override
    public String toString() {
        return "UniversityFacultiesDTO{" +
                "university=" + university +
                ", facultyList=" + facultyList +
                ", lengthOfList=" + lengthOfList +
                '}';
    }
}
