package com.springgoals.model;

import java.util.Objects;

public class Professor {

    private Integer id;

    private String name;

    private String surname;

    private Integer age;

    private String primary_subject1;

    private String primary_subject2;

    private Integer professor_faculty;

    public Professor() {
    }

    public Professor( String name, String surname, String primary_subject1, String primary_subject2, Integer age) {

        this.name = name;
        this.surname = surname;
        this.age = age;
        this.primary_subject1 = primary_subject1;
        this.primary_subject2 = primary_subject2;
    }

    public Professor(String name, String surname, String primary_subject1, String primary_subject2, Integer age, Integer professor_faculty) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.primary_subject1 = primary_subject1;
        this.primary_subject2 = primary_subject2;
        this.professor_faculty = professor_faculty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Professor professor = (Professor) o;
        return Objects.equals(id, professor.id) && Objects.equals(name, professor.name)
                && Objects.equals(surname, professor.surname)
                && Objects.equals(primary_subject1, professor.primary_subject1)
                && Objects.equals(primary_subject2, professor.primary_subject2)
                && Objects.equals(age, professor.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, primary_subject1, primary_subject2, age);
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPrimary_subject1() {
        return primary_subject1;
    }

    public void setPrimary_subject1(String primary_subject1) {
        this.primary_subject1 = primary_subject1;
    }

    public String getPrimary_subject2() {
        return primary_subject2;
    }

    public void setPrimary_subject2(String primary_subject2) {
        this.primary_subject2 = primary_subject2;
    }

    public Integer getProfessor_faculty() {
        return professor_faculty;
    }

    public void setProfessor_faculty(Integer professor_faculty) {
        this.professor_faculty = professor_faculty;
    }

    @Override
    public String toString() {
        return "Professor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", primary_subject1='" + primary_subject1 + '\'' +
                ", primary_subject2='" + primary_subject2 + '\'' +
                '}';
    }
}

