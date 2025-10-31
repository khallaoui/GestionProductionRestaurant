package com.example.gestionproductionrestaurant.Web;
import com.example.gestionproductionrestaurant.Entities.Categorie;
import com.example.gestionproductionrestaurant.Entities.Laboratoire;
import com.example.gestionproductionrestaurant.Entities.Production;
import com.example.gestionproductionrestaurant.Entities.Produit;
import com.example.gestionproductionrestaurant.Metiers.CategorieMetier;
import com.example.gestionproductionrestaurant.Metiers.ProductionMetier;
import com.example.gestionproductionrestaurant.Repository.CategorieRepository;
import com.example.gestionproductionrestaurant.Repository.LaboratoireRepository;
import com.example.gestionproductionrestaurant.Repository.ProductionRepository;
import com.example.gestionproductionrestaurant.Repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
public class ProductionController {
    @Autowired
    LaboratoireRepository laboratoireRepository;
    @Autowired
    private ProductionMetier productionMetier;
    @Autowired
    private ProduitRepository produitRepository;
    @Autowired
    private ProductionRepository productionRepository;
    @Autowired
    private CategorieRepository categorieRepository;
    @Autowired
    CategorieMetier categorieMetier;
    //    @GetMapping("/productions")
//    public String afficherProduction(Model model,@PathVariable long id) {
//        System.out.println("VVVVVVVVVV");
//        Optional<Production> prod=productionMetier.AfficherProduction(id);
//        model.addAttribute("productions", prod);
//        return "show";
//    }
    @GetMapping("produit/{id}/productions")
    public String getProductionsByProduct(@PathVariable("id") Long id, Model model) {
        Produit produit = produitRepository.findById(id).orElse(null);
        List<Production> productions =productionRepository.findByProduitId(id);
        List<Categorie> categories=categorieRepository.findAll();
        model.addAttribute("categories",categories);
        model.addAttribute("produit",produit);
        System.out.println("ssssssssssssssssgggggggggg"+produit);
        model.addAttribute("productions", productions);
        return "show";
    }
    List<Production> productionList=new ArrayList<>();
    @RequestMapping("/productions/create/{id}")
    public String AjouterProduction(Model model,@PathVariable("id")long id) {
        Produit produit = produitRepository.findById(id).orElse(null);
        List<Categorie> categories=categorieRepository.findAll();
        List<Laboratoire> laboratoires=laboratoireRepository.findAll();
        model.addAttribute("categories",categories);
        model.addAttribute("laboratoires",laboratoires);
        Production prod=new Production();
        model.addAttribute("produit",produit);
        model.addAttribute("newproduction",prod);
        return "addProduction";
    }
    @RequestMapping(value = "/productionContr", method = RequestMethod.POST)
    public String postAjouterProduction(@ModelAttribute("newproduction") Production newproduction,@RequestParam("id") long id) {
        System.out.println("----------- ici 1-------------");
        Long categorieId = newproduction.getCategorie().getId();
        Categorie categorie = categorieMetier.getcategorieById(categorieId);
        newproduction.setCategorie(categorie);
        Long laboratoireId=newproduction.getLaboratoire().getId();
        Laboratoire laboratoire=laboratoireRepository.getById(laboratoireId);
        newproduction.setLaboratoire(laboratoire);
        double quantiteUtilisee = newproduction.getQuantite_entre();
        Produit produits=new Produit();
        produits.setNom(newproduction.getTitre());
        produits.setUnite(newproduction.getUnite_sortie());
        produits.setPrix_initial(newproduction.getPrix());
        produits.setStock_en_cours(newproduction.getQuantite_sortie());
        produits.setDescription(newproduction.getDescription());
        produits.setCategorie(newproduction.getCategorie());
        Produit produit = produitRepository.findById(id).orElse(null);
        double stockEnCours = produit.getStock_en_cours();
        produit.setStock_en_cours(stockEnCours - quantiteUtilisee);
        produitRepository.save(produits);
        newproduction.setProduit(produit);
        productionRepository.save(newproduction);
        productionList.add(newproduction);
        newproduction.setProduitApres(produit.getId());

        System.out.println("----------- ici 2-------------");
        return "redirect:produit/"+produit.getId()+"/productions";

        ///" + produit.getId()
    }
    @PostMapping("/production/delete/{id}")
    public String deleteProduction(@PathVariable long id) {
        Production production = productionRepository.findById(id).orElse(null);

        long produitId = production.getProduit().getId();
        productionRepository.delete(production);
        return "redirect:/produit/" + produitId + "/productions";
    }


    @GetMapping("/production/new/{id}")
    public String editproductionForm(@PathVariable long id, Model model) {
        List<Categorie> categories=categorieRepository.findAll();
        List<Laboratoire> laboratoires=laboratoireRepository.findAll();
        model.addAttribute("categories",categories);
        model.addAttribute("laboratoires",laboratoires);
        Produit produit = produitRepository.findById(id).orElse(null);
        System.out.println("ppppppppppppppppggggggggggggffff"+produit);
        model.addAttribute("produit",produit);
        model.addAttribute("newproduction",productionMetier.getProductionByCode  (id));
        return "modifierproduction";

    }

    //    @RequestMapping(value="/production/{id}",method= RequestMethod.POST)
//    public String updateProduction(@PathVariable long id, @ModelAttribute("production") Production newproduction, Model model) {
//        System.out.println("gggg");
//        Production existings =productionMetier.getProductionByCode(id);
//        existings.setUnite_entre(newproduction.getUnite_entre());
//        existings.setUnite_sortie(newproduction.getUnite_sortie());
//        existings.setQuantite_entre(newproduction.getQuantite_entre());
//        existings.setQuantite_sortie(newproduction.getQuantite_sortie());
//        existings.setDescription(newproduction.getDescription());
//        existings.setPerte(newproduction.getPerte());
//        existings.setTitre(newproduction.getTitre());
//        existings.setDate_heure_production(newproduction.getDate_heure_production());
//        existings.setCategorie(newproduction.getCategorie());
//        existings.setLaboratoire(newproduction.getLaboratoire());
//        //existings.setPrix(newproduction.getPrix());
//        Produit produit = existings.getProduit();
//        double quantiteEntreActuelle = existings.getQuantite_entre();
//        double differenceQuantiteEntre = newproduction.getQuantite_entre() - quantiteEntreActuelle;
//        double nouvelleQuantiteStock = produit.getStock_en_cours() + differenceQuantiteEntre;
//        produit.setStock_en_cours(nouvelleQuantiteStock);
//        long produitId = existings.getProduit().getId();
//        productionMetier.modifierprod(existings);
//        return "redirect:/produit/" + produitId + "/productions";
//    }
//
    @RequestMapping(value = "/production/{id}", method = RequestMethod.POST)
    public String updateProduction(@PathVariable long id, @ModelAttribute("production") Production newproduction, Model model) {
        Production existingProduction = productionMetier.getProductionByCode(id);

        existingProduction.setUnite_entre(newproduction.getUnite_entre());
        existingProduction.setUnite_sortie(newproduction.getUnite_sortie());
        existingProduction.setDescription(newproduction.getDescription());
        existingProduction.setTitre(newproduction.getTitre());
        existingProduction.setDate_heure_production(newproduction.getDate_heure_production());
        existingProduction.setCategorie(newproduction.getCategorie());
        existingProduction.setLaboratoire(newproduction.getLaboratoire());
        existingProduction.setQuantite_entre(newproduction.getQuantite_entre());
        existingProduction.setQuantite_sortie(newproduction.getQuantite_sortie());
        existingProduction.setPerte(newproduction.getPerte());
        existingProduction.setPrix(newproduction.getPrix());
        existingProduction.setTemps(newproduction.getTemps());

        // Mise à jour de la quantité du produit dans le stock en cours
        Produit produit = existingProduction.getProduit();
        double ancienneQuantiteEntre = existingProduction.getQuantite_entre();
        double nouvelleQuantiteEntre = newproduction.getQuantite_entre();
        double differenceQuantiteEntre = nouvelleQuantiteEntre - ancienneQuantiteEntre;

        // Mettre à jour la quantité du produit dans le stock en cours
        double ancienneQuantiteStock = produit.getStock_en_cours();
        double nouvelleQuantiteStock = ancienneQuantiteStock + differenceQuantiteEntre;
        produit.setStock_en_cours(nouvelleQuantiteStock);

        // Sauvegarder les modifications
        productionRepository.save(existingProduction);
        produitRepository.save(produit);

        return "redirect:/produit/" + produit.getId() + "/productions";
    }

    @RequestMapping("/home")
    public String affi() {
        return "show";

    }


}
