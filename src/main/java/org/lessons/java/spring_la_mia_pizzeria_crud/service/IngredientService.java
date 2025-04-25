package org.lessons.java.spring_la_mia_pizzeria_crud.service;

import java.util.List;

import org.lessons.java.spring_la_mia_pizzeria_crud.model.Ingredient;
import org.lessons.java.spring_la_mia_pizzeria_crud.model.Pizza;
import org.lessons.java.spring_la_mia_pizzeria_crud.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;

    public List<Ingredient> findAll() {
        return ingredientRepository.findAll();
    }

    public Ingredient findById(Integer id) {
        return ingredientRepository.findById(id).orElse(null);
    }

    public Ingredient save(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    public void delete(Ingredient ingredient) {
        // Rimuove la relazione con le pizze prima della cancellazione
        for (Pizza pizza : ingredient.getPizze()) {
            pizza.getIngredients().remove(ingredient);
        }
        ingredientRepository.delete(ingredient);
    }
}
