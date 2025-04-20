package org.lessons.java.spring_la_mia_pizzeria_crud.controller;

import org.lessons.java.spring_la_mia_pizzeria_crud.model.Offer;
import org.lessons.java.spring_la_mia_pizzeria_crud.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/offerte")
public class OfferController {
    @Autowired
    private OfferRepository repository;

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("offer") Offer formOffer, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "offers/create";
        }
        if (formOffer.getStartDate().isAfter(formOffer.getEndDate())) {
            bindingResult.rejectValue("startDate", "invalidDate", "La data di inizio deve essere prima della data di fine.");
            return "offers/create";
        }
        repository.save(formOffer);
        return "redirect:/pizze/"+formOffer.getPizza().getId();
    }
    
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        Offer offer = repository.findById(id).orElse(null);

        repository.deleteById(id);
        
        return "redirect:/pizze/"+offer.getPizza().getId();
    }
    
    

}
