package org.lessons.java.spring_la_mia_pizzeria_crud.controller;

import java.util.List;

import org.lessons.java.spring_la_mia_pizzeria_crud.model.Pizza;
import org.lessons.java.spring_la_mia_pizzeria_crud.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@CrossOrigin    
@RequestMapping("/api/pizze")
public class PizzaRestController {
    @Autowired
    private PizzaService service;

    @GetMapping
    public List<Pizza> index() {
        List<Pizza> pizze = service.findAll();
        return pizze;
    }
    @GetMapping("/searchByName")
    public List<Pizza> searchByName(@RequestParam(name="name") String  name, Model model) {
        List<Pizza> pizze = service.searchByName(name);
        model.addAttribute("pizze", pizze);
        return pizze;
    } 
    

    @GetMapping("/{id}")
    public ResponseEntity<Pizza> show(@Valid @PathVariable Integer id) {
        Pizza pizza = service.findById(id);
        if (pizza == null) {
            return new ResponseEntity<Pizza>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Pizza>(pizza,HttpStatus.OK);
    }
    

    @PostMapping
    public ResponseEntity<Pizza> store(@Valid @RequestBody Pizza pizza) {
        return new ResponseEntity<Pizza>(service.save(pizza),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pizza> update(@Valid @RequestBody Pizza pizza, @PathVariable Integer id) {
        Pizza existingPizza=service.findById(id);
        if (existingPizza == null) {
            return new ResponseEntity<Pizza>(HttpStatus.NOT_FOUND);
        }
        pizza.setId(id);
        pizza.setIngredients(existingPizza.getIngredients());
        pizza.setOffers(existingPizza.getOffers());    
        return new ResponseEntity<Pizza>(service.save(pizza),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Pizza> delete(@Valid @PathVariable Integer id ) {
        if ( service.findById(id) == null) {
            return new ResponseEntity<Pizza>(HttpStatus.NOT_FOUND);
        }
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    
    

    
}
