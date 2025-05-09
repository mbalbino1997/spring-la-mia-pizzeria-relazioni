package org.lessons.java.spring_la_mia_pizzeria_crud.controller;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.lessons.java.spring_la_mia_pizzeria_crud.model.Offer;
import org.lessons.java.spring_la_mia_pizzeria_crud.model.Pizza;
import org.lessons.java.spring_la_mia_pizzeria_crud.repository.IngredientRepository;
import org.lessons.java.spring_la_mia_pizzeria_crud.repository.PizzaRepository;
import org.lessons.java.spring_la_mia_pizzeria_crud.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
    private PizzaService pizzaService;

    PizzaController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping
    public String index(Authentication authentication,Model model) {
        List<Pizza> pizze = pizzaService.findAll();
        model.addAttribute("pizze", pizze);
        model.addAttribute("username", authentication.getName());
        return "pizze/index";
    }

    @GetMapping("/{id}")
public String show(@PathVariable Integer id, Model model) {
    Pizza pizza = pizzaService.findById(id);
    if (pizza == null) {
        return "redirect:/pizze"; // Se non trova la pizza, torna alla lista
    }
    pizzaService.formatPizzaOffersDates(pizza);
    model.addAttribute("pizza", pizza);
    model.addAttribute("ingredients", ingredientRepository.findAll());
    return "pizze/show"; // Mostra la vista del dettaglio
    }

    @GetMapping("/searchByName")
    public String searchByName(@RequestParam(name="name") String  name, Model model) {
        List<Pizza> pizze = pizzaService.searchByName(name);
        model.addAttribute("pizze", pizze);
        return "pizze/index";
    } 

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("pizza",new Pizza());
        model.addAttribute("ingredients", ingredientRepository.findAll());
        
        return "pizze/create";
    }

    @PostMapping("/create")
    public String store( @Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingresult, Model model) {
        if(bindingresult.hasErrors()) {
            return "pizze/create";
        }
        pizzaService.save(formPizza);
        
        return "redirect:/pizze";
    }


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id,Model model) {
        Pizza pizza = pizzaService.findById(id);
        model.addAttribute("pizza", pizza);
        model.addAttribute("ingredients", ingredientRepository.findAll());
        model.addAttribute("offers",pizza.getOffers());
        return "pizze/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Integer id, @Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingresult, Model model) {
        if(bindingresult.hasErrors()) {
            bindingresult.getAllErrors().forEach(error -> System.out.println(error));
            model.addAttribute("ingredients", ingredientRepository.findAll());
            model.addAttribute("offers", formPizza.getOffers());
            return "pizze/edit";
        }
        formPizza.setOffers(pizzaService.findById(id).getOffers());
        pizzaService.save(formPizza);
        
        return "redirect:/pizze";
    }


    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        pizzaService.deleteById(id);
        return "redirect:/pizze";
    }
    

    @GetMapping("{id}/offers")
    public String offer(@PathVariable Integer id,Model model) {
        Offer offer = new Offer();
        offer.setPizza(pizzaService.findById(id)); 
        model.addAttribute("offer", offer);
        return "offers/create";
    }

    


    
    
    
    

    
}
