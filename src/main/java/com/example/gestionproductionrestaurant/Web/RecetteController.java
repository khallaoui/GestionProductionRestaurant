package com.example.gestionproductionrestaurant.Web;


import com.example.gestionproductionrestaurant.Entities.*;
import com.example.gestionproductionrestaurant.Metiers.CategorieRecetteMetier;
import com.example.gestionproductionrestaurant.Metiers.RecetteMetier;
import com.example.gestionproductionrestaurant.Repository.CategorieRecetteRepository;
import com.example.gestionproductionrestaurant.Repository.LigneRecetteRepository;
import com.example.gestionproductionrestaurant.Repository.ProduitRepository;
import com.example.gestionproductionrestaurant.Repository.RecetteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.*;
@Controller
public class RecetteController {
    @Autowired
    RecetteRepository recetteRepository;
    @Autowired
    ProduitRepository produitRepository;
    @Autowired
    CategorieRecetteRepository categorieRecetteRepository;
    @Autowired
    LigneRecetteRepository ligneRecetteRepository;
    @Autowired
    RecetteMetier recetteMetier;
    @Autowired
    CategorieRecetteMetier categorieRecetteMetier;

    @GetMapping("/recet")
    public String AfficherRecette(Model model,RedirectAttributes redirectAttributes) {
       try { List<Recette> lstrec = recetteRepository.findAll();
        List<Categorie_recette> categorie_recettes = categorieRecetteRepository.findAll();
        Collections.sort(lstrec, (recette1, recette2) -> Long.compare(recette2.getId(), recette1.getId()));
        model.addAttribute("categorie_recettes", categorie_recettes);

        model.addAttribute("recette", lstrec);
        System.out.println("aff recette");
    } catch (Exception e) {
        e.printStackTrace(); // Affiche la trace de la pile de l'exception
        redirectAttributes.addFlashAttribute("error", "Une erreur s'est produite lors de l'envoi la liste des recettes " + e.getMessage());
    }  return "recette";
    }
    /********************************************************************************************/
    @GetMapping("/recette/create")
    public String showCreateForm(Model model,RedirectAttributes redirectAttributes) {
       try {
           List<Produit> produits = produitRepository.findAll();
        model.addAttribute("recette", new Recette());
        model.addAttribute("ligneRecette",new Ligne_Recette());
        model.addAttribute("categorie_recette", categorieRecetteRepository.findAll());
        model.addAttribute("produits", produits);
        System.out.println("kkkkkkkkkkkkkkkkkk");

    } catch (Exception e) {
        e.printStackTrace(); // Affiche la trace de la pile de l'exception
        redirectAttributes.addFlashAttribute("error", "Une erreur s'est produite lors de l'envoi la formulaire ajouter recettes " + e.getMessage());
    }  return "create-recette2";
    }
    /********************************************************************************************/

    @PostMapping("/recette/save")
    public String saveRecette(@ModelAttribute("recette") Recette recette, @RequestBody List<RecetteData> recetteDataList, Model model
            , RedirectAttributes redirectAttributes) {
        try {
        for (RecetteData recetteData : recetteDataList) {
            Long categorierecetteId = recetteData.getRecette().getCategorie_recette().getId();
            Categorie_recette categorierecette = categorieRecetteMetier.getcategorierecetById(categorierecetteId);
            recetteData.getRecette().setCategorie_recette(categorierecette);
            Recette savedRecette = recetteRepository.save(recetteData.getRecette());
            double coutTotal = 0.0;
            List<Ligne_Recette> ligneRecettes = recetteData.getLigneRecettes();
            System.out.println("rrrrrrrrrrrrrrrrrrrrrrrrrrr"+ligneRecettes);
            for (Ligne_Recette ligneRecette : ligneRecettes) {
                System.out.println("le cout total est:"+coutTotal);
                Produit produit = produitRepository.findById(ligneRecette.getProduitId()).orElse(null);
                double coutUnite = produit.getPrix_initial();
                double quantite = ligneRecette.getQuantite();
                double coutLigneRecette = coutUnite * quantite;
                coutTotal += coutLigneRecette;
                ligneRecette.setRecette(savedRecette);
                ligneRecette.setProduit(produit);
                System.out.println("rrrrrrrrrrrrrrrrrrrrrrrrrrr"+ligneRecette);
                ligneRecetteRepository.save(ligneRecette);
                System.out.println("rrrrrrrrrrrrrddddddddddrrrrrrrrrrrrrr"+ligneRecette);
            }
            savedRecette.setCout_total(coutTotal);
            recetteRepository.save(savedRecette);
        }
        // Redirection avec un message de succès
        redirectAttributes.addFlashAttribute("success", "La recette a été enregistrée avec succès.");
        } catch (Exception e) {
            e.printStackTrace(); // Affiche la trace de la pile de l'exception
            redirectAttributes.addFlashAttribute("error", "Une erreur s'est produite lors de l'envoi la formulaire ajouter recettes " + e.getMessage());
        }  return "redirect:/recet";
    }
    /********************************************************************************************/

    @RequestMapping("/recette/delete/{id}")
    public String delete(@PathVariable Long id,  RedirectAttributes redirectAttributes) {
        try {
            System.out.println("\n\n\n\n cat =" + id + "\n\n\n\n\n");
            recetteRepository.deleteById(id);
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("error", "la recette est est lié avec d'autre clé étrangère on ne pas ");
        }

        return "redirect:/recet";
    }
    @GetMapping("/recette/new/{id}")
    public String editrecetteForm(Model model, @PathVariable Long id,RedirectAttributes redirectAttribute) {
        try{
            Optional<Recette> recette = recetteRepository.findById(id);
            model.addAttribute("recette", recette);

            List<Ligne_Recette> ligneRecettes = ligneRecetteRepository.findByRecette(recette);
            model.addAttribute("ligneRecettes", ligneRecettes);

            List<Categorie_recette> categorie_recette = categorieRecetteRepository.findAll();
            model.addAttribute("categorie_recette",categorie_recette);
            List<Produit> produits = produitRepository.findAll();
            model.addAttribute("produits", produits);
        }catch (Exception e){
            redirectAttribute.addFlashAttribute("error", e.getMessage());
        }
        return "editrecette";

    }
    @RequestMapping(value = "/recette/{id}", method = RequestMethod.POST)
    public String updaterecette(@PathVariable Long id,RedirectAttributes redirectAttributes
            , @RequestBody List<RecetteData> recettelist, Model model) {
        try {
            Recette rec1 = recetteRepository.getById(id);
            if (!recettelist.isEmpty()) {
                RecetteData recetteDataList = recettelist.get(0);
                Recette recette = recetteDataList.getRecette();

                rec1.setDesignation(recette.getDesignation());
                rec1.setDate_limite_consomation(recette.getDate_limite_consomation());
                rec1.setCategorie_recette(recette.getCategorie_recette());

                // Ajouter les nouvelles lignes de recettes à la recette existante
                List<Ligne_Recette> nouvellesLignesRecettes = recetteDataList.getLigneRecettes();
                System.out.println("nouvellesLignesRecettes: " + nouvellesLignesRecettes);

                // Associer les nouvelles lignes de recettes à la recette existante
                List<Ligne_Recette> ligneRecettes = rec1.getLigneRecettes();
                System.out.println("ligneRecettes: " + ligneRecettes);

                ligneRecettes.addAll(nouvellesLignesRecettes);
                System.out.println("ligneRecettes après: " + ligneRecettes);

                // Associer la recette à chaque nouvelle ligne de recette
                for (Ligne_Recette ligneRecette : nouvellesLignesRecettes) {
                    Produit produit = produitRepository.findById(ligneRecette.getProduitId()).orElse(null);
                    ligneRecette.setProduit(produit);
                    ligneRecette.setRecette(rec1);
                }
                Double cout=rec1.getCout_total();
                System.out.println("cout avant ajout"+cout);
                for (Ligne_Recette ligneRecette : nouvellesLignesRecettes) {
                    cout += ligneRecette.getProduit().getPrix_initial()*ligneRecette.getQuantite(); // Ajoutez le coût de chaque produit à la recette
                }
                System.out.println("cout apres ajout"+cout);
                rec1.setCout_total(cout);
                // Enregistrer les nouvelles lignes de recettes dans la base de données
                ligneRecetteRepository.saveAll(nouvellesLignesRecettes);
            }
            // Sauvegarder les modifications apportées à la recette
            recetteRepository.save(rec1);
            System.out.println("la recette: " + rec1);
            redirectAttributes.addFlashAttribute("sus", "La recette a été enregistrée avec succès.");

        } catch (Exception e) {
            e.printStackTrace(); // Affiche la trace de la pile de l'exception
            redirectAttributes.addFlashAttribute("error", "Une erreur s'est produite lors de validation la modification d'un recette" + e.getMessage());
        }

        return "redirect:/recet";
    }

    /********************************************************************************************/
    @RequestMapping("/ligne/delete/{id}")
    public String deleteligne(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            System.out.println("\n\n\n\n ID =" + id + "\n\n\n\n\n");
            Ligne_Recette ligneRecette = ligneRecetteRepository.findById(id).orElse(null);
            if (ligneRecette != null) {
                // Récupérer la recette associée à la ligne de recette
                Recette recette = ligneRecette.getRecette();
                // Supprimer la ligne de recette
                ligneRecetteRepository.deleteById(id);

                // Mettre à jour le coût total de la recette
                Double coutTotal = 0.0;
                List<Ligne_Recette> ligneRecettes = recette.getLigneRecettes();
                for (Ligne_Recette ligne : ligneRecettes) {
                    coutTotal += ligne.getProduit().getPrix_initial() * ligne.getQuantite();
                }
                recette.setCout_total(coutTotal);
                recetteRepository.save(recette);
            }
            System.out.println("id ligne" + id);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "La ligne est liée à d'autres clés étrangères et ne peut pas être supprimée.");
        }

        return "redirect:/recette/create";
    }

}
