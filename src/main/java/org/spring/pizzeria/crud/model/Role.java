package org.spring.pizzeria.crud.model;

import jakarta.persistence.*;

@Entity
@Table(name = "role")
public class Role {

    @Id
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    //getter e setter
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
}
