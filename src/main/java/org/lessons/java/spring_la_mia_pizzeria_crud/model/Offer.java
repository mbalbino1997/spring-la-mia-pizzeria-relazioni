package org.lessons.java.spring_la_mia_pizzeria_crud.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="offerte")
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="pizza_id", nullable = false)
    @JsonBackReference
    private Pizza pizza;
    
    @Size(min=5,max=100)
    @NotBlank(message="Il titolo non può essere vuoto")
    @Column(name="title")
    private String title;
    
    @Column(nullable = false, precision = 10, scale = 2)
    @Min(value=0,message="Il prezzo non può essere minore di 0")
    private BigDecimal price;

    @NotNull(message = "La data di inizio non può essere nulla")
    @FutureOrPresent(message= "L'offerta non può iniziare nel passato")
    @Column(name = "start_date")
    private LocalDate startDate;
    
    @NotNull(message = "La data di fine non può essere nulla")
    @Column(name = "end_date")
    private LocalDate endDate;

    
    private String startDateFormatted;
    private String endDateFormatted;

    public String getStartDateFormatted() {
        return startDateFormatted;
    }

    public void setStartDateFormatted(String startDateFormatted) {
        this.startDateFormatted = startDateFormatted;
    }

    public String getEndDateFormatted() {
        return endDateFormatted;
    }

    public void setEndDateFormatted(String endDateFormatted) {
        this.endDateFormatted = endDateFormatted;
    }

    
    public Integer getId() {
        return id;
    }
    
     public void setId(Integer id) {
         this.id = id;
     }
     public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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
    
    public Pizza getPizza() {
        return pizza;
    }
    
    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }
}
