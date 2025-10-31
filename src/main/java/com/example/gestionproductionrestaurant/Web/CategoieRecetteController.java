package com.example.gestionproductionrestaurant.Web;
import com.example.gestionproductionrestaurant.Entities.Categorie_recette;
import com.example.gestionproductionrestaurant.Metiers.CategorieRecetteMetier;
import com.example.gestionproductionrestaurant.Repository.CategorieRecetteRepository;
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
public class  CategoieRecetteController {
    @Autowired
    CategorieRecetteRepository categorieRecetteRepository;
    @Autowired
    CategorieRecetteMetier categorieRecetteMetier;


    @GetMapping("/categorirecet")
    public String AfficherCategorie(Model model) {
        System.out.println("aff");
        List<Categorie_recette> lstcat=categorieRecetteRepository.findAll();
        model.addAttribute("categorierecette",lstcat);
        return "categorierecette";
    }

    @RequestMapping("/addcatrecet")
    public String AjouterCategorieRecette(Model model) {
        Categorie_recette caterece = new Categorie_recette();
        model.addAttribute("categorierecet", caterece);
        return "addcategorierecette";
    }

    @RequestMapping(value = "/PostCategorieRecet", method = RequestMethod.POST)
    public String postAjouterCategorieRecette(@Valid @ModelAttribute("categorierecet") Categorie_recette categorierecette,  BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder("");
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                errorMessages.append("* ").append(fieldError.getDefaultMessage()).append("");
            }
            System.out.println("eeeeeeeeeeeeeeeeee" + errorMessages.toString());
            model.addAttribute("errors", errorMessages.toString());
            return "addcategorierecette";
        }
        try {
        categorieRecetteRepository.save(categorierecette);
        } catch (Exception e) {
            e.printStackTrace(); // Affiche la trace de la pile de l'exception
            redirectAttributes.addFlashAttribute("error", "Une erreur s'est produite lors de l'envoie de la formulaire d'ajouter une catégorie: " + e.getMessage());
        }return "redirect:/categorirecet";
    }

    @RequestMapping("/categorierecet/delete/{id}")
    public String delete(@PathVariable Long id,RedirectAttributes redirectAttributes) {
       try { System.out.println("\n\n\n\n cat =" + id + "\n\n\n\n\n");
        categorieRecetteRepository.deleteById(id);
    } catch (Exception e) {
        e.printStackTrace(); // Affiche la trace de la pile de l'exception
        redirectAttributes.addFlashAttribute("error", "Une erreur s'est produite lors de la suppresion de la catégorie: " + e.getMessage());
    }
       return "redirect:/categorirecet";
    }

    @GetMapping("/categorierecet/new/{id}")
    public String editcategorieForm(@PathVariable Long id, Model model) {
        System.out.println("modif acces");
        model.addAttribute("categorierec", categorieRecetteRepository.findById(id));
        return "editcategorierecette";

    }

    @RequestMapping(value = "/categorierecet/{id}")
    public String updateCategorie(@PathVariable Long id, @Valid @ModelAttribute("categorierec") Categorie_recette categorierec, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder("");
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                errorMessages.append("* ").append(fieldError.getDefaultMessage()).append("");
            }
            model.addAttribute("categorierec", categorieRecetteRepository.findById(id));

            System.out.println("eeeeeeeeeeeeeeeeee" + errorMessages.toString());
            model.addAttribute("errors", errorMessages.toString());
            return "editcategorierecette";
        }
        try {

        Categorie_recette cat2 =categorieRecetteMetier.getcategorierecetById(id);
        cat2.setLibelle(categorierec.getLibelle());
        categorieRecetteMetier.modifiercate(cat2);
        } catch (Exception e) {
            e.printStackTrace(); // Affiche la trace de la pile de l'exception
            redirectAttributes.addFlashAttribute("error", "Une erreur s'est produite lors de l'envoie de la formulaire d'modifier une catégorie: " + e.getMessage());
        }return "redirect:/categorirecet";
    }
}

