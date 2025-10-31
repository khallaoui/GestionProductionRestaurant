package com.example.gestionproductionrestaurant.Web;

import com.example.gestionproductionrestaurant.Entities.Fournisseur;
import com.example.gestionproductionrestaurant.Entities.Jour;
import com.example.gestionproductionrestaurant.Entities.JourDisponible;
import com.example.gestionproductionrestaurant.Metiers.FournisseurMetier;
import com.example.gestionproductionrestaurant.Repository.CategorieRepository;
import com.example.gestionproductionrestaurant.Repository.FournisseurRepository;
import com.example.gestionproductionrestaurant.Repository.JourDisponibleRepository;
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
public class FournisseurController {
    @Autowired
    FournisseurMetier fournisseurMetier;
    @Autowired
    FournisseurRepository fournisseurRepository;

    @Autowired
    JourDisponibleRepository jourDisponibleRepository;

    //    @GetMapping(value="/index")
//    public String index() {
//        return "fournisseurp";
//    }
    @GetMapping("/fourni")
    public String AfficherFournisseur(Model model,RedirectAttributes redirectAttributes){
        try{
            List<Fournisseur> lstfour=fournisseurMetier.afficherfournisseur();
        model.addAttribute("fournisseurs",lstfour);
        System.out.println("aff fournisseur");
    } catch (Exception e) {
        e.printStackTrace(); // Affiche la trace de la pile de l'exception
        redirectAttributes.addFlashAttribute("error", "Une erreur s'est produite lors de l'envoi du fourmulaire afficher un Fournisseur: " + e.getMessage());
    } return  "fournisseur";
    }
    @GetMapping("/addfour")
    public String afficherFormulaireAjoutFournisseur(Model model, RedirectAttributes redirectAttributes) {
      try {  model.addAttribute("fournisseur", new Fournisseur());
        model.addAttribute("jours", Jour.values());
    } catch (Exception e) {
        e.printStackTrace(); // Affiche la trace de la pile de l'exception
        redirectAttributes.addFlashAttribute("error", "Une erreur s'est produite lors de l'envoi du fourmulaire ajouter un Fournisseur: " + e.getMessage());
    }  return "addfOUR";
    }

    @PostMapping("/postfour")
    public String enregistrerFournisseur(@Valid @ModelAttribute("fournisseur")  Fournisseur fournisseur, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("");
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                errorMessage.append("* ").append(fieldError.getDefaultMessage()).append("");
            }
            model.addAttribute("jours", Jour.values());

            System.out.println("eeeeeeeeeeeeeeeeee"+errorMessage.toString());
            model.addAttribute("errors", errorMessage.toString());
            return "addfOUR";
        }
     try {   fournisseurRepository.save(fournisseur);
        for (Jour jour : Jour.values()) {
            JourDisponible jourDisponible = new JourDisponible();
            jourDisponible.setFournisseur(fournisseur);
            fournisseur.getJoursDisponibles().add(String.valueOf(jourDisponible));
        }

    } catch (Exception e) {
        e.printStackTrace(); // Affiche la trace de la pile de l'exception
        redirectAttributes.addFlashAttribute("error", "Une erreur s'est produite lors de la sauvgarde du Fournisseur: " + e.getMessage());
    } return "redirect:/fourni";
    }











    @RequestMapping("/fournisseur/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
      try {
          System.out.println("\n\n\n\n four ="+id+"\n\n\n\n\n");
        fournisseurMetier.deleteFournisseur(id);
    } catch (Exception e) {
        e.printStackTrace(); // Affiche la trace de la pile de l'exception
        redirectAttributes.addFlashAttribute("error", "Une erreur s'est produite lors de la suppression du Fournisseur: " + e.getMessage());
    }   return "redirect:/fourni";
    }
    @GetMapping("/fournisseur/new/{id}")
    public String editfournisseurForm(@PathVariable Long id,Model model,RedirectAttributes redirectAttributes) {
        try {
            System.out.println("modif acces");
        model.addAttribute("fournisseur",fournisseurMetier.getFournisseurById(id));
        model.addAttribute("jours", Jour.values());
    } catch (Exception e) {
        e.printStackTrace(); // Affiche la trace de la pile de l'exception
        redirectAttributes.addFlashAttribute("error", "Une erreur s'est produite lors de l'envoi du fourmulaire modifier le Fournisseur: " + e.getMessage());
    }   return "editfournisseur";

    }
    @RequestMapping(value="/fournisseur/{id}")
    public String updateFournisseur(@PathVariable Long id, @Valid @ModelAttribute("fournisseur") Fournisseur fournisseur, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("");
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                errorMessage.append("* ").append(fieldError.getDefaultMessage()).append("");
            }
            model.addAttribute("fournisseur",fournisseurMetier.getFournisseurById(id));
            model.addAttribute("jours", Jour.values());

            System.out.println("dddddddd"+errorMessage.toString());
            model.addAttribute("error", errorMessage.toString());
            return "editfournisseur";
        }
        try{ System.out.println("gggg");
        Fournisseur four1=fournisseurMetier.getFournisseurById((id));
        four1.setNom(fournisseur.getNom());
        four1.setVille(fournisseur.getVille());
        four1.setCode_Fournisseur(fournisseur.getCode_Fournisseur());
        four1.setMax_delai_livraison(fournisseur.getMax_delai_livraison());
        four1.setDescription(fournisseur.getDescription());
        four1.setPrenom(fournisseur.getPrenom());
        four1.setPrincipal(fournisseur.isPrincipal());
        four1.setMax_delai_Paiement(fournisseur.getMax_delai_Paiement());
        four1.setMax_quantite_Recommandee(fournisseur.getMax_quantite_Recommandee());
        four1.setTelephone(fournisseur.getTelephone());
        four1.setMin_quantite_Recommandee(fournisseur.getMin_quantite_Recommandee());
        four1.setJoursDisponibles(fournisseur.getJoursDisponibles());
        fournisseurMetier.modifierFournisseur(four1);
    } catch (Exception e) {
        e.printStackTrace(); // Affiche la trace de la pile de l'exception
        redirectAttributes.addFlashAttribute("error", "Une erreur s'est produite lors de validation du fourmulaire modifier le Fournisseur: " + e.getMessage());
    }  return "redirect:/fourni";
    }



}


    //
//    @RequestMapping(value = "/postfour", method = RequestMethod.POST)
//public String postajouterFournisseur(@ModelAttribute("fournisseur") Fournisseur fournisseur,@ModelAttribute("jour") com.example.gestiondeproduction1.entities.Jour jour) {
//
//            Jour_Fournisseur jourFournisseur = new Jour_Fournisseur(jour, fournisseur, true);
//        //jourFournisseur.setJour(jour.get(1));
//        jourFournisseur.setJour_disponible(true); // Par défaut, le jour est disponible
//        jourFournisseur.setFournisseur(fournisseur);
//            fournisseurMetier.AjouterFournisseur(fournisseur);
//            jourFournisseurMetier.Ajouterjourfour(jourFournisseur);
//
//    // fournisseurMetier.AjouterFournisseur(fournisseur);
//    return "redirect:/fourni";
//}
//
//    @RequestMapping(value = "/postfour", method = RequestMethod.POST)
//    public String postAjouterCategorie(@ModelAttribute("fournisseur") Fournisseur fournisseur,Model model) {
////        jourMetier.getjourById(id);
//        fournisseurMetier.AjouterFournisseur(fournisseur);
//        return "redirect:/fourni";
//    }
//@GetMapping("/addfour")
//public String ajouterFournisseur(Model model) {
//    List<com.example.gestiondeproduction1.entities.Jour> jour = jourRepository.findAll(); // Récupérer tous les jours existants
//    model.addAttribute("jour", jour);
//    model.addAttribute("fournisseur", new Fournisseur());
//    return "addfOUR";
//}
//
//    @PostMapping("/postfour")
//    public String sauvegarderFournisseur(@ModelAttribute Fournisseur fournisseur,
//                                         @RequestParam("jourIds") List<Long> jourIds) {
//        List<com.example.gestiondeproduction1.entities.Jour> joursDisponibles = new ArrayList<>();
//        for (Long jourId : jourIds) {
//            com.example.gestiondeproduction1.entities.Jour jour = jourRepository.findById(jourId).orElse(null);
//            if (jour != null) {
//                joursDisponibles.add(jour);
//            }
//        }
//
//        // Créer une nouvelle association entre le fournisseur et les jours disponibles
//        Jour_Fournisseur association = JourFournisseurMetier.creerAssociation(fournisseur, joursDisponibles);
//        jourFournisseurMetier.Ajouterjourfour(association);
//        return "redirect:/fourni";
//    }


