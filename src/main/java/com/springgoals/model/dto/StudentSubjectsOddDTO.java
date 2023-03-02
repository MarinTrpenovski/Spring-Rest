package com.springgoals.model.dto;

import com.springgoals.model.Subject;

import java.util.List;
import java.util.Objects;

public class StudentSubjectsOddDTO {

    private Integer studentId;

    private  String studentName;

    List<Subject> subjectList;

    private Integer lengthOfList;

    private Integer indeks;

    private Integer sumOfCredits;


    public StudentSubjectsOddDTO() {}

    public StudentSubjectsOddDTO(Integer studentId, String studentName, List<Subject> subjectList, Integer lengthOfList) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.subjectList = subjectList;
        this.lengthOfList = lengthOfList;
    }

    public StudentSubjectsOddDTO(Integer studentId, String studentName, List<Subject> subjectList, Integer lengthOfList, Integer indeks, Integer sumOfCredits) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.subjectList = subjectList;
        this.lengthOfList = lengthOfList;
        this.indeks = indeks;
        this.sumOfCredits = sumOfCredits;
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

    public Integer getIndeks() {
        return indeks;
    }

    public void setIndeks(Integer indeks) {
        this.indeks = indeks;
    }

    public Integer getSumOfCredits() {
        return sumOfCredits;
    }

    public void setSumOfCredits(Integer sumOfCredits) {
        this.sumOfCredits = sumOfCredits;
    }

    @Override
    public String toString() {
        return "StudentSubjectsOddDTO{" +
                "studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                ", subjectList=" + subjectList +
                ", lengthOfList=" + lengthOfList +
                ", indeks=" + indeks +
                ", sumOfCredits=" + sumOfCredits +
                '}';
    }
}
