package org.spring.pizzeria.crud.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

//ASSEGNO ENTITA' E CAMBIO IL NOME NELLA TABELLA
@Entity
@Table(name = "pizze")
public class Pizza {

    //PK AUTO INCREMENT
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //ALTRI CAMPI

    private String name;

    //LOB per allungare la descrizionde come se fossee text in mySQL
    @Lob
    private String description;

    private BigDecimal price;


    //getter e setter di tutti i campi


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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
