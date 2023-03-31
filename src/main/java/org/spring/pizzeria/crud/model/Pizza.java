package org.spring.pizzeria.crud.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

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

    @NotEmpty(message = "Devi inserire il nome")
    private String name;

    //LOB per allungare la descrizionde come se fossee text in mySQL
    @Lob
    @NotEmpty(message = "Devi inserire la descrizione")
    private String description;

    @NotNull(message = "Devi inserire il prezzo")
    @PositiveOrZero(message = "Il prezzo deve essere superiore o uguale a 0")
    private BigDecimal price;

    private String image;

    public Pizza() {
        super();
    }

    public Pizza(String name, String description, BigDecimal price, String image) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
    }

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
