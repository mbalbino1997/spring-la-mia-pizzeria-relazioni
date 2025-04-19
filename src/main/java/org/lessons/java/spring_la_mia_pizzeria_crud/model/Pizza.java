package org.lessons.java.spring_la_mia_pizzeria_crud.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Entity
@Table(name="pizze")
public class Pizza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Size(min= 0,max=50, message ="Il nome non può essere più lungo di 50 caratteri")
    @NotBlank(message="Name non puo essere vuoto")
    @Column(name="name")
    private String name;
    @Size(min=10,max=500)
    @NotBlank(message="La descrizione non può essere vuota")
    private String description;
    private String url;
    @Column(nullable = false, precision = 10, scale = 2)
    @Min(value=0,message="Il prezzo non può essere minore di 0")
    private BigDecimal price;
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
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("nome: %s, descrizione: %s, prezzo: %.2f€ ", name, description, price);
    }

    
}
