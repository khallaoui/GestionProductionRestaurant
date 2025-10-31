package com.example.gestionproductionrestaurant.Web;

import com.example.gestionproductionrestaurant.Entities.Categorie;
import com.example.gestionproductionrestaurant.Entities.Zone_stockage;
import com.example.gestionproductionrestaurant.Metiers.CategorieMetier;
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
//@RequestMapping(value = "/api")
public class CategorieController {
    @Autowired
    CategorieMetier categorieMetier;
    @GetMapping("/")
    public String hello() {
        return "navbar"; // renvoie la vue Thymeleaf nommée "hello"
    }
    @GetMapping("/categorie")
    public String AfficherCategorie(Model model, RedirectAttributes redirectAttributes){
      try {
        System.out.println("aff");
        List<Categorie> cat=categorieMetier.AfficherCategorie();
        model.addAttribute("categories", cat);
    } catch (Exception e) {
        e.printStackTrace(); // Affiche la trace de la pile de l'exception
        redirectAttributes.addFlashAttribute("error", "Une erreur s'est produite lors de l'affichage de la catégorie: " + e.getMessage());
    } return "Categories";
    }


   @RequestMapping("/addcat")
    public String AjouterCategorie(Model model, RedirectAttributes redirectAttributes) {
 try {
        Categorie cate = new Categorie();
        model.addAttribute("categorie", cate);
   } catch (Exception e) {
        e.printStackTrace(); // Affiche la trace de la pile de l'exception
        redirectAttributes.addFlashAttribute("error", "Une erreur s'est produite lors de chargement de la page ajouter catégorie: " + e.getMessage());
    }
        return "addcategorie";
    }

    @RequestMapping("/categorie/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
      try {
        System.out.println("\n\n\n\n cat ="+id+"\n\n\n\n\n");
        categorieMetier.deletecat(id);
    } catch (Exception e) {
        e.printStackTrace(); // Affiche la trace de la pile de l'exception
        redirectAttributes.addFlashAttribute("error", "Une erreur s'est produite lors de la suppresion de la catégorie: " + e.getMessage());
    }
        return "redirect:/categorie";
    }

    @RequestMapping(value = "/PostCat", method = RequestMethod.POST)
    public String postAjouterMarque(@Valid @ModelAttribute("categorie") Categorie categorie , BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder("");
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                errorMessages.append("* ").append(fieldError.getDefaultMessage()).append("");
            }
            System.out.println("eeeeeeeeeeeeeeeeee" + errorMessages.toString());
            model.addAttribute("errors", errorMessages.toString());
            return "addcategorie";
        }
      try {
        categorieMetier.AjouterCategorie(categorie);
    } catch (Exception e) {
        e.printStackTrace(); // Affiche la trace de la pile de l'exception
        redirectAttributes.addFlashAttribute("error", "Une erreur s'est produite lors de l'envoie de la formyulaire d'ajouter une catégorie: " + e.getMessage());
    }
        return "redirect:/categorie";
    }

    @GetMapping("/categorie/new/{id}")
    public String editcategorieForm(@PathVariable Long id,Model model) {


        model.addAttribute("categorie", categorieMetier.getcategorieById(id));
        return "EditCategorie";}

    @RequestMapping(value="/categorie/{id}")
    public String updateCategorie(@PathVariable Long id, @Valid @ModelAttribute("categorie") Categorie categorie, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("");
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                errorMessage.append("* ").append(fieldError.getDefaultMessage()).append("");
            }
            System.out.println("eeeeeeeeeeeeeeeeee"+errorMessage.toString());
            model.addAttribute("categorie", categorieMetier.getcategorieById(id));
            model.addAttribute("errors", errorMessage.toString());
            return "EditCategorie";
        }
        try {
           Categorie cat1 = categorieMetier.getcategorieById(id);
                cat1.setLibelle(categorie.getLibelle());
                categorieMetier.modifiercate(cat1);
        } catch (Exception e) {
            e.printStackTrace(); // Affiche la trace de la pile de l'exception
            redirectAttributes.addFlashAttribute("error", "Une erreur s'est produite lors de la modification de la catégorie: " + e.getMessage());
        }
        return "redirect:/categorie";



    /*@RequestMapping("/Ajouterfour")
    public Fournisseur AjouterFournisseur(){
        Fournisseur f1=new Fournisseur();
        return f1;
    }*/
        //@PostMapping("/categories")
    /*@RequestMapping(value = "/categories", method = RequestMethod.POST)
    public void SaveCategorie(@RequestBody Categorie categorie){
        if(categorie.getLibelle() != null){
            categorieMetier.AjouterCategorie(categorie);
        } else {
            System.out.println("Invalid Categorie: " + categorie.toString());
        }
    }*/
    }
}
