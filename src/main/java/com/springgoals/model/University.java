package com.springgoals.model;

import java.util.Objects;


public class University {

    private Integer id;

    private String name;

    private String description;

    public University(){}
    public University(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        University university = (University) o;
        return Objects.equals(id, university.id) && Objects.equals(name, university.name)
                && Objects.equals(description, university.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
