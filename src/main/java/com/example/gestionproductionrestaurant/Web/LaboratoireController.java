package com.example.gestionproductionrestaurant.Web;

import com.example.gestionproductionrestaurant.Entities.Laboratoire;
import com.example.gestionproductionrestaurant.Metiers.LaboratoireMetier;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
public class LaboratoireController {

    @Autowired
    LaboratoireMetier laboratoireMetier;
    @GetMapping("/laboratoire")
    public String AfficherLaboratoire(Model model){
        System.out.println("aff");
        List<Laboratoire> lab=laboratoireMetier.AfficherLaboratoire();
        model.addAttribute("laboratoires",lab);
        return "laboratoires";
    }
    @RequestMapping("/addlab")
    public String AjouterLaboratoire(Model model) {
        Laboratoire lab = new Laboratoire();
        model.addAttribute("laboratoire", lab);
        return "addlaboratoire";
    }
    @RequestMapping(value = "/PostLaboratoire", method = RequestMethod.POST)
    public String postAjouterlaboratoire(@Valid @ModelAttribute("laboratoire") Laboratoire laboratoire,BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder("");
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                errorMessages.append("* ").append(fieldError.getDefaultMessage()).append("");
            }
            System.out.println("eeeeeeeeeeeeeeeeee" + errorMessages.toString());
            model.addAttribute("errors", errorMessages.toString());
            return "addlaboratoire";
        }
        try{
        laboratoireMetier.AjouterLaboratoire(laboratoire);
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Une erreur s'est produite lors de l'envoi du formulaire modifier une marque: " + e.getMessage());
        }return "redirect:/laboratoire";
    }
    @RequestMapping("/laboratoire/delete/{id}")
    public String delete(@PathVariable Long id) {
        System.out.println("\n\n\n\n lab ="+id+"\n\n\n\n\n");
        laboratoireMetier.deleteclab(id);
        return "redirect:/laboratoire";
    }
    @GetMapping("/laboratoire/new/{id}")
    public String editlaboratoireForm(@PathVariable Long id,Model model) {
        System.out.println("modif acces");
        model.addAttribute("laboratoire",laboratoireMetier.getlaboratoireById(id));
        return "EditLaboratoire";

    }
    @RequestMapping(value="/laboratoire/{id}")
    public String updateLaboratoire(@PathVariable Long id, @Valid @ModelAttribute("laboratoire") Laboratoire laboratoire, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder("");
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                errorMessages.append("* ").append(fieldError.getDefaultMessage()).append("");
            }
            model.addAttribute("laboratoire",laboratoireMetier.getlaboratoireById(id));
            System.out.println("eeeeeeeeeeeeeeeeee" + errorMessages.toString());
            model.addAttribute("errors", errorMessages.toString());
            return "EditLaboratoire";
                }
        try {
                System.out.println("gggg");
                Laboratoire lab1=laboratoireMetier.getlaboratoireById(id);
                lab1.setNom(laboratoire.getNom());
                laboratoireMetier.modifierlab(lab1);
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Une erreur s'est produite lors de l'envoi du formulaire modifier une marque: " + e.getMessage());
        }return "redirect:/laboratoire";
    }



}

