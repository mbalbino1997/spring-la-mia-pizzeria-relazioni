package org.lessons.java.spring_la_mia_pizzeria_crud.controller;

import org.lessons.java.spring_la_mia_pizzeria_crud.model.Offer;
import org.lessons.java.spring_la_mia_pizzeria_crud.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/offerte")
public class OfferController {

    @Autowired
    private OfferService offerService;

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("offer") Offer formOffer, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "offers/create";
        }

        if (!offerService.isStartDateBeforeEndDate(formOffer)) {
            bindingResult.rejectValue("startDate", "invalidDate", "La data di inizio deve essere prima della data di fine.");
            return "offers/create";
        }

        offerService.save(formOffer);
        return "redirect:/pizze/" + formOffer.getPizza().getId();
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        Offer offer = offerService.findById(id);
        if (offer == null) {
            return "redirect:/pizze";
        }

        offerService.deleteById(id);
        return "redirect:/pizze/" + offer.getPizza().getId();
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Offer offer = offerService.findById(id);
        if (offer == null) {
            return "redirect:/pizze";
        }
        model.addAttribute("offer", offer);
        return "offers/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("offer") Offer formOffer, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "offers/edit";
        }

        if (!offerService.isStartDateBeforeEndDate(formOffer)) {
            bindingResult.rejectValue("startDate", "invalidDate", "La data di inizio deve essere prima della data di fine.");
            return "offers/edit";
        }

        offerService.save(formOffer);
        return "redirect:/pizze/" + formOffer.getPizza().getId();
    }
}
