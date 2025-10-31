package com.example.gestionproductionrestaurant.Web;

import com.example.gestionproductionrestaurant.Repository.CategorieRepository;
import com.example.gestionproductionrestaurant.Repository.FournisseurRepository;
import com.example.gestionproductionrestaurant.Repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class DashboardController {
    @Autowired
    FournisseurRepository fournisseurRepository;
  @Autowired
  ProduitRepository produitRepository;
  @Autowired
  CategorieRepository categorieRepository ;

    @GetMapping("/Dashboard")
    public String AfficherDash(Model model){
        long nombreTotalFournisseurs = fournisseurRepository.countTotalFournisseurs();
        System.out.println("Nombre total de fournisseurs : " + nombreTotalFournisseurs);
        model.addAttribute("nombreFournisseur",nombreTotalFournisseurs);
        long nombreTotalProduit = produitRepository.countTotalProduits();
        System.out.println("Nombre total de produit : " + nombreTotalProduit);
        model.addAttribute("nombreTotalProduit",nombreTotalProduit);
        long nombrecat = categorieRepository.countTotalCategories();
        System.out.println("Nombre total de cat : " + nombrecat);
        model.addAttribute("nombrecat",nombrecat);

        return  "dashboared";
    }
}
