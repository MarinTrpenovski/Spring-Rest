package com.springgoals.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


import java.util.List;
import java.util.Objects;

public class Role {

    private Integer id;

    @NotNull(message = "name must be between 2 and 20 characters")
    @Size(min = 2, max = 20)
    private String name;

    private List<Permission> permissions;

    public Role () {}

    public Role( String name) {

        this.name = name;
    }

    public Role( String name, List<Permission> permissions) {

        this.name = name;
        this.permissions = permissions;
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

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return id.equals(role.id) && name.equals(role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
