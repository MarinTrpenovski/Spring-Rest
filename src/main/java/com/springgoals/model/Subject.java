package com.springgoals.model;

import java.util.Objects;

public class Subject {
    private Integer id;

    private String name;

    private String semester;

    private Integer credits;

    private Integer subject_professor;

    public Subject() {
    }


    public Subject( String name, String semester, Integer credits) {

        this.name = name;
        this.semester = semester;
        this.credits = credits;
    }

    public Subject(String name, String semester, Integer credits, Integer subject_professor) {
        this.name = name;
        this.semester = semester;
        this.credits = credits;
        this.subject_professor = subject_professor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return Objects.equals(id, subject.id) && Objects.equals(name, subject.name)
                && Objects.equals(semester, subject.semester)

                && Objects.equals(credits, subject.credits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, semester, credits);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public Integer getSubject_professor() {
        return subject_professor;
    }

    public void setSubject_professor(Integer subject_professor) {
        this.subject_professor = subject_professor;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", semester='" + semester + '\'' +
                ", credits=" + credits +
                '}';
    }
}
