package com.springgoals.model.dto;

import com.springgoals.model.Subject;

import java.util.List;
import java.util.Objects;

public class FacultyDTO {

    private Integer facultyId;

    private String facultyName;

    List<Subject> subjectList;

    private Integer lengthOfList;

    public FacultyDTO () {}

    public FacultyDTO(Integer facultyId, String facultyName, List<Subject> subjectList, Integer lengthOfList) {
        this.facultyId = facultyId;
        this.facultyName = facultyName;
        this.subjectList = subjectList;
        this.lengthOfList = lengthOfList;
    }

    public Integer getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(Integer facultyId) {
        this.facultyId = facultyId;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public List<Subject> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(List<Subject> subjectList) {
        this.subjectList = subjectList;
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
        FacultyDTO that = (FacultyDTO) o;
        return facultyId.equals(that.facultyId) && facultyName.equals(that.facultyName) && subjectList.equals(that.subjectList) && lengthOfList.equals(that.lengthOfList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(facultyId, facultyName, subjectList, lengthOfList);
    }

    @Override
    public String toString() {
        return "FacultyDTO{" +
                "facultyId=" + facultyId +
                ", facultyName='" + facultyName + '\'' +
                ", subjectList=" + subjectList +
                ", lengthOfList=" + lengthOfList +
                '}';
    }
}
