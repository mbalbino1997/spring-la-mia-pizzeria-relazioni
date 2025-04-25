package org.lessons.java.spring_la_mia_pizzeria_crud.controller;

import java.util.List;

import org.lessons.java.spring_la_mia_pizzeria_crud.model.Ingredient;
import org.lessons.java.spring_la_mia_pizzeria_crud.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/ingredienti")
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    @GetMapping
    public String index(Model model) {
        List<Ingredient> ingredienti = ingredientService.findAll();
        model.addAttribute("ingredienti", ingredienti);
        return "ingredients/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("ingredient", new Ingredient());
        return "ingredients/create";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("ingredient") Ingredient formIngredient,
                        BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "ingredients/create";
        }
        ingredientService.save(formIngredient);
        return "redirect:/ingredienti";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Ingredient ingredient = ingredientService.findById(id);
        if (ingredient == null) {
            return "redirect:/ingredienti";
        }
        model.addAttribute("ingredient", ingredient);
        return "ingredients/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("ingredient") Ingredient formIngredient,
                         BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "ingredients/edit";
        }
        ingredientService.save(formIngredient);
        return "redirect:/ingredienti";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        Ingredient ingredient = ingredientService.findById(id);
        if (ingredient != null) {
            ingredientService.delete(ingredient);
        }
        return "redirect:/ingredienti";
    }
}
