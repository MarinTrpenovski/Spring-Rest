package com.springgoals.model.dto;

import com.springgoals.model.Faculty;

import java.util.List;
import java.util.Objects;

public class UniversityDTO {

    private Integer universityId;

    private String universityName;

    List<Faculty> facultyList;

    private Integer lengthOfList;

    public UniversityDTO() {

    }

    public UniversityDTO(Integer universityId, String universityName, List<Faculty> facultyList, Integer lengthOfList ) {
        this.universityId = universityId;
        this.universityName = universityName;
        this.facultyList = facultyList;
        this.lengthOfList = lengthOfList;
    }

    public Integer getUniversityId() {
        return universityId;
    }

    public void setUniversityId(Integer universityId) {
        this.universityId = universityId;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
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
        UniversityDTO universityDTO = (UniversityDTO) o;
        return universityId.equals(universityDTO.universityId) &&
                universityName.equals(universityDTO.universityName) &&
                facultyList.equals(universityDTO.facultyList) &&
                lengthOfList.equals(universityDTO.lengthOfList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(universityId, universityName, facultyList);
    }

    @Override
    public String toString() {
        return "UniversityDTO{" +
                "universityId=" + universityId +
                ", universityName='" + universityName + '\'' +
                ", facultyList=" + facultyList +
                ", lengthOfList=" + lengthOfList +
                '}';
    }

}
