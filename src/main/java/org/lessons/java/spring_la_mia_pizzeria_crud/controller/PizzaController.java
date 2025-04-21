package org.lessons.java.spring_la_mia_pizzeria_crud.controller;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.lessons.java.spring_la_mia_pizzeria_crud.model.Offer;
import org.lessons.java.spring_la_mia_pizzeria_crud.model.Pizza;
import org.lessons.java.spring_la_mia_pizzeria_crud.repository.IngredientRepository;
import org.lessons.java.spring_la_mia_pizzeria_crud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/pizze")
public class PizzaController {

    private final IngredientRepository ingredientRepository;
    @Autowired
    private PizzaRepository repository;

    PizzaController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping
    public String index(Model model) {
        List<Pizza> pizze = repository.findAll();
        model.addAttribute("pizze", pizze);
        return "pizze/index";
    }

    @GetMapping("/{id}")
public String show(@PathVariable Integer id, Model model) {
    Pizza pizza = repository.findById(id).orElse(null);
    if (pizza == null) {
        return "redirect:/pizze"; // Se non trova la pizza, torna alla lista
    }
     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    pizza.getOffers().forEach(offer -> {
        // Controlla che la data di inizio non sia nulla
        if (offer.getStartDate() != null) {
            String startDateFormatted = offer.getStartDate().format(formatter);
            offer.setStartDateFormatted(startDateFormatted); // Imposta la data formattata
        } else {
            offer.setStartDateFormatted(""); // Imposta una stringa vuota se la data è nulla
        }
        
        // Controlla che la data di fine non sia nulla
        if (offer.getEndDate() != null) {
            String endDateFormatted = offer.getEndDate().format(formatter);
            offer.setEndDateFormatted(endDateFormatted); // Imposta la data formattata
        } else {
            offer.setEndDateFormatted(""); // Imposta una stringa vuota se la data è nulla
        }
    });
    model.addAttribute("pizza", pizza);
    return "pizze/show"; // Mostra la vista del dettaglio
    }
    @GetMapping("/searchByName")
    public String searchByName(@RequestParam(name="name") String  name, Model model) {
        List<Pizza> pizze = repository.findByNameContaining(name);
        model.addAttribute("pizze", pizze);
        return "pizze/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("pizza",new Pizza());
        
        return "pizze/create";
    }

    @PostMapping("/create")
    public String store( @Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingresult, Model model) {
        if(bindingresult.hasErrors()) {
            return "pizze/create";
        }
        repository.save(formPizza);
        
        return "redirect:/pizze";
    }


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id,Model model) {
        Pizza pizza = repository.findById(id).orElse(null);
        model.addAttribute("pizza", pizza);
        model.addAttribute("ingredients", ingredientRepository.findAll());
    if (pizza == null) {
        return "redirect:/pizze";
    }
        return "pizze/edit";
    }

    @PostMapping("/edit/{id}")
    public String update( @Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingresult, Model model) {
        if(bindingresult.hasErrors()) {
            return "pizze/edit";
        }
        repository.save(formPizza);
        
        return "redirect:/pizze";
    }


    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        repository.deleteById(id);
        return "redirect:/pizze";
    }
    

    @GetMapping("{id}/offers")
    public String offer(@PathVariable Integer id,Model model) {
        Offer offer = new Offer();
        offer.setPizza(repository.findById(id).get());
        model.addAttribute("offer", offer);
        return "offers/create";
    }

    


    
    
    
    

    
}
