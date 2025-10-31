package com.example.gestionproductionrestaurant.Web;

import com.example.gestionproductionrestaurant.Entities.Zone_stockage;
import com.example.gestionproductionrestaurant.Metiers.Zone_StockageMetier;
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
public class Zone_stockageController {

    @Autowired
    Zone_StockageMetier zoneStockageMetier ;
    @GetMapping("/Zones")
    public String AfficherZone(Model model){
        System.out.println("aff");
        List<Zone_stockage> zones=zoneStockageMetier.AfficherZone_stockage();
        model.addAttribute("zones", zones);
        return "Zones";
    }

    @RequestMapping("/addZone")
    public String AjouterZones(Model model,RedirectAttributes redirectAttributes) {
        try {Zone_stockage zone = new Zone_stockage();
        model.addAttribute("zones", zone);
    }catch (Exception e) {
        e.printStackTrace(); // Affiche la trace de la pile de l'exception
        redirectAttributes.addFlashAttribute("error", "Une erreur s'est produite lors de l'envoi de la formulaire d'ajouter d'une zone de stockage: " + e.getMessage());
    } return "addZone";
    }
    @RequestMapping(value = "/PostZone", method = RequestMethod.POST)
    public String postAjouterMarque(@Valid @ModelAttribute("zones") Zone_stockage zone_stockage , BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder("");
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                errorMessages.append("* ").append(fieldError.getDefaultMessage()).append("");
            }
            System.out.println("eeeeeeeeeeeeeeeeee" + errorMessages.toString());
            model.addAttribute("errors", errorMessages.toString());
            return "addZone";
        }

    try {    zoneStockageMetier.AjouterZone_stockage(zone_stockage);
    }catch (Exception e) {
        e.printStackTrace(); // Affiche la trace de la pile de l'exception
        redirectAttributes.addFlashAttribute("error", "Une erreur s'est produite lors de l'envoi de la formulaire d'ajouter d'une zone de stockage: " + e.getMessage());
    }   return "redirect:/Zones";
    }
    @RequestMapping("/Zone/delete/{id}")
    public String delete(@PathVariable Long id,RedirectAttributes redirectAttributes) {
      try {  System.out.println("\n\n\n\n marq ="+id+"\n\n\n\n\n");
        zoneStockageMetier.deleteZone_stockage(id);
    }catch (Exception e) {
        e.printStackTrace(); // Affiche la trace de la pile de l'exception
        redirectAttributes.addFlashAttribute("error", "Une erreur s'est produite lors de la suppression d'une zone de stockage: " + e.getMessage());
    }return "redirect:/Zones";
    }
    @GetMapping("/Zone/new/{id}")
    public String editZoneForm(@PathVariable Long id,Model model, RedirectAttributes redirectAttributes) {
       try { System.out.println("modif acces");
        model.addAttribute("zone",zoneStockageMetier.getZone_stockageById(id));
    }catch (Exception e) {
        e.printStackTrace(); // Affiche la trace de la pile de l'exception
        redirectAttributes.addFlashAttribute("error", "Une erreur s'est produite lors de l'envoi de la formulaire de modification d'une zone de stockage: " + e.getMessage());
    } return "Editzone";

    }
    @RequestMapping(value="/Zone/{id}")
    public String updateZone(@PathVariable Long id,@Valid @ModelAttribute("zone") Zone_stockage zone_stockage,  BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder("");
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                errorMessages.append("* ").append(fieldError.getDefaultMessage()).append("");
            }
            System.out.println("eeeeeeeeeeeeeeeeee" + errorMessages.toString());
            model.addAttribute("zone",zoneStockageMetier.getZone_stockageById(id));
            model.addAttribute("errors", errorMessages.toString());
            return "Editzone";
        }
        try { System.out.println("gggg");
        Zone_stockage zone =zoneStockageMetier.getZone_stockageById(id);
        zone.setDesignation(zone_stockage.getDesignation());
        zoneStockageMetier.modifierZone_stockage(zone);
    }catch (Exception e) {
        e.printStackTrace(); // Affiche la trace de la pile de l'exception
        redirectAttributes.addFlashAttribute("error", "Une erreur s'est produite lors de la modification d'une zone de stockage: " + e.getMessage());
    }return "redirect:/Zones";
    }
}
