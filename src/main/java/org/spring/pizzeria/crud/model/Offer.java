package org.spring.pizzeria.crud.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "offer")
public class Offer {

    //variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //relations
    @ManyToOne
    private Pizza pizza;

//    @NotEmpty(message = "La data di inizio è obbligatoria")
    @FutureOrPresent(message = "La data di inizio deve essere futura")
    private LocalDate startDate;

//    @NotEmpty(message = "La data di fine è obbligatoria")
    @FutureOrPresent(message = "La data di fine deve essere futura")
    private LocalDate endDate;

    @NotNull(message = "Il titolo è obbligatorio")
    private String title;


    //contructor
    public Offer() {
    }

    public Offer(Integer id, LocalDate startDate, LocalDate endDate, String title) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
    }


    //getter e setter

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
