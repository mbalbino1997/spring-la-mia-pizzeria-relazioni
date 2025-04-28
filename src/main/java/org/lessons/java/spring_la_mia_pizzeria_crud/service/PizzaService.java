package org.lessons.java.spring_la_mia_pizzeria_crud.service;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.lessons.java.spring_la_mia_pizzeria_crud.model.Pizza;
import org.lessons.java.spring_la_mia_pizzeria_crud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PizzaService {
    @Autowired
    private PizzaRepository pizzaRepository;

    public List<Pizza> findAll() {
        return pizzaRepository.findAll();
    }
    public Pizza findById(Integer id) {
        return pizzaRepository.findById(id).orElse(null);
    }

    public void formatPizzaOffersDates(Pizza pizza) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        pizza.getOffers().forEach(offer -> {
            if (offer.getStartDate() != null) {
                String startDateFormatted = offer.getStartDate().format(formatter);
                offer.setStartDateFormatted(startDateFormatted);
            } else {
                offer.setStartDateFormatted("");
            }
            if (offer.getEndDate() != null) {
                String endDateFormatted = offer.getEndDate().format(formatter);
                offer.setEndDateFormatted(endDateFormatted);
            } else {
                offer.setEndDateFormatted("");
            }
        });
    }

    public Pizza save(Pizza pizza) {
        pizzaRepository.save(pizza);
        return pizza;
    }

    public void deleteById(Integer id) {
        pizzaRepository.deleteById(id);
    }

    public List<Pizza> searchByName(String name) {
        return pizzaRepository.findByNameContainingIgnoreCase(name);
    }

    
}
