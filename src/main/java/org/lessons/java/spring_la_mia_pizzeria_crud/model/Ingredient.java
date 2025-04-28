package org.lessons.java.spring_la_mia_pizzeria_crud.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="ingredienti")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Size(min=0,max=50, message="L'ingrediente non può essere più lungo di 50 caratteri")
    @NotBlank(message = "Il nome dell'ingrediente non può essere vuoto")
    @Column(name="name")
    private String name;
    
    @Size(min=10,max=500)
    @NotBlank(message="La descrizione non può essere vuota")
    private String description;
    
    @ManyToMany(mappedBy="ingredients")
    @JsonIgnore
    private List<Pizza> pizze;
    
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

    public List<Pizza> getPizze() {
        return pizze;
    }

    public void setPizze(List<Pizza> pizze) {
        this.pizze = pizze;
    }


    
}
