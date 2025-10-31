package com.example.gestionproductionrestaurant.Web;
import com.example.gestionproductionrestaurant.Entities.Fournisseur;
import com.example.gestionproductionrestaurant.Entities.Jour;
import com.example.gestionproductionrestaurant.Entities.Marque;
import com.example.gestionproductionrestaurant.Metiers.MarqueMetier;
import com.example.gestionproductionrestaurant.Repository.MarqueRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
@Controller
public class MarqueController {

        @Autowired
        MarqueMetier marqueMetier;
        @Autowired
        MarqueRepository marqueRepository ;
        @GetMapping("/Marques")
        public String AfficherMarque(Model model, RedirectAttributes redirectAttributes){
         try{
             System.out.println("aff");
            List<Marque> marques=marqueMetier.AfficherMarque();
            model.addAttribute("marques", marques);
        }catch (Exception e) {
        e.printStackTrace(); // Affiche la trace de la pile de l'exception
        redirectAttributes.addFlashAttribute("error", "Une erreur s'est produite lors de l'affichage de la liste des Marques: " + e.getMessage());
    }return "Marques";
        }

        @RequestMapping("/addMarque")
        public String AjouterMarque(Model model,RedirectAttributes redirectAttributes) {
          try {  Marque marq = new Marque();
            model.addAttribute("marques", marq);
        }catch (Exception e) {
        e.printStackTrace(); // Affiche la trace de la pile de l'exception
        redirectAttributes.addFlashAttribute("error", "Une erreur s'est produite lors de l'affichage de la formulaire d'jouter un nouvelle Marque: " + e.getMessage());
    }return "addMarque";
        }
    @RequestMapping(value = "/PostMarque", method = RequestMethod.POST)
    public String postAjouterMarque(@Valid @ModelAttribute("marques") Marque marque, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder("");
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                errorMessages.append("* ").append(fieldError.getDefaultMessage()).append("");
            }
            System.out.println("eeeeeeeeeeeeeeeeee" + errorMessages.toString());
            model.addAttribute("errors", errorMessages.toString());
            return "addMarque";
        }

        try {
            marqueMetier.AjouterMarque(marque);
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Une erreur s'est produite lors de l'envoi du formulaire pour ajouter une nouvelle marque: " + e.getMessage());
        }

        return "redirect:/Marques";
    }


    @RequestMapping("/Marque/delete/{id}")
        public String delete(@PathVariable Long id ,    RedirectAttributes redirectAttributes) {
         try {   System.out.println("\n\n\n\n marq ="+id+"\n\n\n\n\n");
            marqueMetier.deleteMarque(id);
        }catch (Exception e) {
        e.printStackTrace(); // Affiche la trace de la pile de l'exception
        redirectAttributes.addFlashAttribute("error", "Une erreur s'est produite lors de la suppression de la Marque: " + e.getMessage());
    }  return "redirect:/Marques";
        }
        @GetMapping("/Marque/new/{id}")
        public String editMarqueForm(@PathVariable Long id,Model model,RedirectAttributes redirectAttributes) {
         try {
             System.out.println("modif acces");
            model.addAttribute("marque",marqueMetier.getMarqueById(id));
         }catch (Exception e) {
             e.printStackTrace(); // Affiche la trace de la pile de l'exception
             redirectAttributes.addFlashAttribute("error", "Une erreur s'est produite lors de l'envoi de la formulaire modifier une Marque: " + e.getMessage());
         } return "EditMarque";

        }
        @RequestMapping(value="/Marque/{id}")
        public String updateMarque(@PathVariable Long id, @Valid @ModelAttribute("marque") Marque marque, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
            if (bindingResult.hasErrors()) {
                StringBuilder errorMessage = new StringBuilder("");
                List<FieldError> fieldErrors = bindingResult.getFieldErrors();
                for (FieldError fieldError : fieldErrors) {
                    errorMessage.append("* ").append(fieldError.getDefaultMessage()).append("");
                }
                System.out.println("eeeeeeeeeeeeeeeeee"+errorMessage.toString());
                model.addAttribute("marque",marqueMetier.getMarqueById(id));
                model.addAttribute("errors", errorMessage.toString());
                return "EditMarque";
            }
          try {  System.out.println("gggg");
            Marque marq=marqueMetier.getMarqueById(id);
            marq.setLibelle(marque.getLibelle());
            marq.setDescription(marque.getDescription());
            marqueMetier.modifierMarque(marq);
        }catch (Exception e) {
        e.printStackTrace(); // Affiche la trace de la pile de l'exception
        redirectAttributes.addFlashAttribute("error", "Une erreur s'est produite lors de la modification de la Marque: " + e.getMessage());
    } return "redirect:/Marques";
        }

    }
