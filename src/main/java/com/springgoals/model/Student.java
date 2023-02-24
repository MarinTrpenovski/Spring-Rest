package com.springgoals.model;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull(message = "name must be between 1 and 45 characters")
    @Size(min = 1, max = 45)
    private String name;

    @NotNull(message = "surname must be between 1 and 45 characters")
    @Size(min = 1, max = 45)
    private String surname;

    @NotNull(message = "location must be between 1 and 45 characters")
    @Size(min = 1, max = 45)
    private String location;

    @NotNull(message = "indeks must not be null")
    @Min(value = 1, message = "indeks should not be 0")
    private Integer indeks;

    //    List<Subject> subjectList;
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
