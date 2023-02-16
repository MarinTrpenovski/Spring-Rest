package com.springgoals.model.dto;

import com.springgoals.model.Student;
import com.springgoals.model.Subject;

import java.util.Objects;

public class UpdateStudentSubjectDTO {

    private Subject subject;
    private Student student;

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateStudentSubjectDTO that = (UpdateStudentSubjectDTO) o;
        return subject.equals(that.subject) && student.equals(that.student);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, student);
    }
}
