package com.springgoals.model;

import java.util.Objects;

public class Faculty {

    private Integer id;
    private String name;
    private String location;
    private String study_field;

    private Integer university_id;
    public Faculty(int i, String location, String study_field) {
    }

    public Faculty(String name, String location, String study_field) {
        this.name = name;
        this.location = location;
        this.study_field = study_field;
    }

    public Faculty(String name, String location, String study_field, Integer university_id) {
        this.name = name;
        this.location = location;
        this.study_field = study_field;
        this.university_id = university_id;
    }

    public Faculty() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Faculty faculty = (Faculty) o;
        return Objects.equals(id, faculty.id) && Objects.equals(name, faculty.name)
                && Objects.equals(location, faculty.location)
                && Objects.equals(study_field, faculty.study_field);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, location, study_field);
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStudy_field() {
        return study_field;
    }

    public void setStudy_field(String study_field) {
        this.study_field = study_field;
    }

    public Integer getUniversity_id() {
        return university_id;
    }

    public void setUniversity_id(Integer university_id) {
        this.university_id = university_id;
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", study_field='" + study_field + '\'' +
                '}';
    }
}