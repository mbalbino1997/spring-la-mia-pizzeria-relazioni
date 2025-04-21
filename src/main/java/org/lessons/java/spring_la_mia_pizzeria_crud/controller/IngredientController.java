package org.lessons.java.spring_la_mia_pizzeria_crud.controller;

import java.util.List;

import org.lessons.java.spring_la_mia_pizzeria_crud.model.Ingredient;
import org.lessons.java.spring_la_mia_pizzeria_crud.model.Pizza;
import org.lessons.java.spring_la_mia_pizzeria_crud.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/ingredienti")
public class IngredientController {
    @Autowired
    private IngredientRepository ingredientRepository;

    @GetMapping
    public String index(Model model) {
        List<Ingredient> ingredienti = ingredientRepository.findAll();
        model.addAttribute("ingredienti", ingredienti);
        return "ingredients/index";
    }

    // @GetMapping("/{id}")
    // public String show(@PathVariable Integer id, Model model) {
    //     Ingredient ingredient= ingredientRepository.findById(id).orElse(null);
    //     if (ingredient == null) {
    //         return "redirect:/ingredienti";
    //     }
    //     return "ingredients/show";
    // }
    
    @GetMapping("edit/{id}")
    public String edit(@PathVariable Integer id,Model model) {
        Ingredient ingredient = ingredientRepository.findById(id).orElse(null);
        model.addAttribute("ingredient", ingredient);
        if (ingredient == null) {
            return "redirect:/ingredienti";
        }
        return "ingredients/edit";
    }

    @PostMapping("edit/{id}")
    public String postMethodName(@Valid @ModelAttribute("ingredient") Ingredient formIngredient, BindingResult bindingresult, Model model) {
        if (bindingresult.hasErrors()) {
            return "ingredients/edit";
        }
        ingredientRepository.save(formIngredient);
        
        return "redirect:/ingredienti";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("ingredient",new Ingredient());
        
        return "ingredients/create";
    }

    @PostMapping("/create")
    public String store( @Valid @ModelAttribute("ingredient") Ingredient formIngredient, BindingResult bindingresult, Model model) {
        if(bindingresult.hasErrors()) {
            return "ingredients/create";
        }
        ingredientRepository.save(formIngredient);
        
        return "redirect:/ingredienti";
    }
    
    



    
}
