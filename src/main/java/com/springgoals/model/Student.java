package com.springgoals.model;

import java.util.Objects;

public class Student {

    private Integer id;

    private String name;

    private String surname;

    private String location;

    private Integer indeks;

    public Student() {
    }

    public Student(String name, String surname, Integer indeks, String location) {
        this.name = name;
        this.surname = surname;
        this.location = location;
        this.indeks = indeks;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id) && Objects.equals(name, student.name)
                && Objects.equals(surname, student.surname)
                && Objects.equals(location, student.location)
                && Objects.equals(indeks, student.indeks)
                ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, location, indeks);
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getIndeks() {
        return indeks;
    }

    public void setIndeks(Integer indeks) {
        this.indeks = indeks;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", location='" + location + '\'' +
                ", indeks=" + indeks +

                '}';
    }
}
