package com.springgoals.model;

import java.util.Objects;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class Student {

    private Integer id;

    @NotNull(message = "name must be between 2 and 20  characters")
    @Size(min = 2, max = 20)
    private String name;

    @NotNull(message = "surname must be between 2 and 20  characters")
    @Size(min = 2, max = 20)
    private String surname;

    @NotNull(message = "location must be between 2 and 20  characters")
    @Size(min = 2, max = 20)
    private String location;

    @NotNull(message = "index must not be null")
    @Min(value = 1, message = "index should not be 0")
    private Integer indeks;

    private String imagePath;

    //    List<Subject> subjectList;
    public Student() {
    }

    public Student(String name, String surname, Integer indeks, String location) {
        this.name = name;
        this.surname = surname;
        this.location = location;
        this.indeks = indeks;
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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
