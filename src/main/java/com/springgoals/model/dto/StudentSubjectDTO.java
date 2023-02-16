package com.springgoals.model.dto;

import com.springgoals.model.Subject;

import java.util.List;
import java.util.Objects;

public class StudentSubjectDTO {

    private Integer studentId;

    private  String studentName;

    List<Subject> subjectList;

    private Integer lengthOfList;

    public StudentSubjectDTO() {}

    public StudentSubjectDTO(Integer studentId, String studentName, List<Subject> subjectList, Integer lengthOfList) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.subjectList = subjectList;
        this.lengthOfList = lengthOfList;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
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
        StudentSubjectDTO that = (StudentSubjectDTO) o;
        return studentId.equals(that.studentId) && studentName.equals(that.studentName) && subjectList.equals(that.subjectList) && lengthOfList.equals(that.lengthOfList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, studentName, subjectList, lengthOfList);
    }

    @Override
    public String toString() {
        return "StudentSubjectDTO{" +
                "studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                ", subjectList=" + subjectList +
                ", lengthOfList=" + lengthOfList +
                '}';
    }

}
