package com.example.gestionproductionrestaurant.Web;



import com.example.gestionproductionrestaurant.Entities.*;
import com.example.gestionproductionrestaurant.Metiers.FournisseurMetier;
import com.example.gestionproductionrestaurant.Metiers.ReceptionMetier;
import com.example.gestionproductionrestaurant.Repository.FournisseurRepository;
import com.example.gestionproductionrestaurant.Repository.LigneReceptionRepository;
import com.example.gestionproductionrestaurant.Repository.ProduitRepository;
import com.example.gestionproductionrestaurant.Repository.ReceptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ReceptionController {

    @Autowired
    ReceptionRepository receptionRepository;
    @Autowired
    ProduitRepository produitRepository;
    @Autowired
    LigneReceptionRepository ligneReceptionRepository;
    @Autowired
    ReceptionMetier receptionMetier;
    @Autowired
    FournisseurMetier fournisseurMetier;
    @Autowired
    FournisseurRepository fournisseurRepository;

    @GetMapping("/recep")
    public String AfficherReception(Model model) {
        List<Bon_Reception> listbon = receptionRepository.findAll();
        // List<Ligne_Recette> ligne_recettes = ligneRecetteRepository.findAll();
        //model.addAttribute("categorie_recettes", categorie_recettes);
        List<Fournisseur> fournisseur = fournisseurRepository.findAll();
        model.addAttribute("fournisseur", fournisseur);
        model.addAttribute("reception", listbon);
        // model.addAttribute("jour", j);
        // model.addAttribute("ligne_recettes", ligne_recettes);
        // System.out.println(ligne_recettes);
        System.out.println("aff reception");
        return "showbonreception";
    }


    @GetMapping("/reception/create")
    public String showCreateForm(Model model) {
        List<Produit> produits = produitRepository.findAll();
        model.addAttribute("reception", new Bon_Reception());
        model.addAttribute("produits", produits);

        model.addAttribute("ligneReception", new Ligne_Reception());
        model.addAttribute("fournisseur", fournisseurRepository.findAll());
        System.out.println("create reception");

        return "addReception";
    }


    @PostMapping("/recep/save")
    public String saveReception(@ModelAttribute("reception") Bon_Reception bon_reception, @RequestBody List<ReceptionData> receptionDataList, RedirectAttributes redirectAttributes) {
        System.out.println("entrer en fonction");
        System.out.println(receptionDataList);
        // Enregistrement des lignes de reception
        for (ReceptionData receptionData : receptionDataList) {
            long fournisseurId = receptionData.getBon_reception().getFournisseur().getId();
            Fournisseur fournisseur = fournisseurMetier.getFournisseurById(fournisseurId);
            receptionData.getBon_reception().setFournisseur(fournisseur);


            Bon_Reception savedReception = receptionRepository.save(receptionData.getBon_reception());
            System.out.println(savedReception);
            double total_HT = 0.0;

            //savedRecette.setCategorie_recette(categorierecette);
            List<Ligne_Reception> ligneReceptions = receptionData.getLigneReceptions();
            for (Ligne_Reception ligneReception : ligneReceptions) {
                Produit produit = produitRepository.findById(ligneReception.getProduitId()).orElse(null);
                double prix_produit = ligneReception.getPrix_achat();
                double remise = ligneReception.getMontant_remise();
                double quantite_livree = ligneReception.getQuantite_livree();
                double coutLigneReception = (prix_produit * quantite_livree) - remise;
                total_HT += coutLigneReception;
                //Set the savedRecette for each ligneRecette
                ligneReception.setBonreception(savedReception);
                ligneReception.setProduit(produit);
                ligneReceptionRepository.save(ligneReception);
                System.out.println("save reception");
            }
            savedReception.setTotal_HT(total_HT);
            receptionRepository.save(savedReception);

        }

        // Redirection avec un message de succès
        redirectAttributes.addFlashAttribute("success", "Le bon reception  a été enregistrée avec succès.");

        return "redirect:/recep";
    }

    //        @GetMapping("/homeee")
//    public String redirectToAuthentification() {
//        return "bonlivraison";
//    }
//    // Redirige vers une page de liste des recettes ou une autre page appropriée
//    }
//    @GetMapping("/bon_recu/new/{id}")
//    public String recuForm(@PathVariable Long id, Model model) {
//        System.out.println("modif acces");
//        model.addAttribute("bon_recep",receptionMetier.getrecuById(id));
//
//        return "Recu_bonreception";
//
//    }
    @GetMapping("/bon_recu/new/{id}")
    public String recuForm(Model model, @PathVariable Long id,RedirectAttributes redirectAttribute) {
        try{
            Bon_Reception bon_reception = receptionRepository.findById(id).orElse(null);
            model.addAttribute("bon_recep", bon_reception);

            List<Ligne_Reception> ligneReceptions = ligneReceptionRepository.findByBonreception(bon_reception);
            model.addAttribute("ligneReceptions", ligneReceptions);

//            List<Categorie_recette> categorie_recette = categorieRecetteRepository.findAll();
//        model.addAttribute("ligneRecette", ligneRecetteRepository.findById(id));
//            model.addAttribute("categorie_recette",categorie_recette);
            List<Produit> produits = produitRepository.findAll();
            model.addAttribute("produits", produits);
            // model.addAttribute("donneesList", donneesList);
        }catch (Exception e){
            redirectAttribute.addFlashAttribute("error", e.getMessage());

        }
        return "bonlivraison";

    }

    @RequestMapping("/reception/delete/{id}")
    public String delete(@PathVariable Long id,  RedirectAttributes redirectAttributes) {
        try {
            System.out.println("\n\n\n\n cat =" + id + "\n\n\n\n\n");
            receptionRepository.deleteById(id);
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("error", "le bon reception est est lié avec d'autre clé étrangère on ne peut  pas le supprimer ");
        }

        return "redirect:/recep";
    }

    @GetMapping("/editbon/new/{id}")
    public String editbonForm(Model model, @PathVariable Long id,RedirectAttributes redirectAttribute) {
        try{
            Bon_Reception bon_reception = receptionRepository.findById(id).orElse(null);
            model.addAttribute("bon_recep", bon_reception);

            List<Ligne_Reception> ligneReceptions = ligneReceptionRepository.findByBonreception(bon_reception);
            model.addAttribute("ligneReceptions", ligneReceptions);

            List<Fournisseur> fournisseur = fournisseurRepository.findAll();
            model.addAttribute("fournisseur", fournisseur);
//            model.addAttribute("categorie_recette",categorie_recette);
            List<Produit> produits = produitRepository.findAll();
            model.addAttribute("produits", produits);
            // model.addAttribute("donneesList", donneesList);
        }catch (Exception e){
            redirectAttribute.addFlashAttribute("error", e.getMessage());

        }
        return "editbonreception";

    }
    @RequestMapping(value = "/reception/{id}", method = RequestMethod.POST)
    public String updaterecette(@PathVariable Long id, @RequestBody List<ReceptionData> receptionlist, Model model) {
        try {
            Bon_Reception recep = receptionRepository.getById(id);
            if (!receptionlist.isEmpty()) {
                ReceptionData receptionDataList = receptionlist.get(0);
                Bon_Reception bon_reception = receptionDataList.getBon_reception();

                recep.setFournisseur(bon_reception.getFournisseur());
                recep.setDate_livraison(bon_reception.getDate_livraison());
                recep.setDate_commande(bon_reception.getDate_livraison());
                recep.setVersement(bon_reception.getVersement());
                recep.setTotal_HT(bon_reception.getTotal_HT());
                recep.setNumero_bon(bon_reception.getNumero_bon());
                recep.setNumero_bon_annee(bon_reception.getNumero_bon_annee());



                // Ajouter les nouvelles lignes de recettes à la recette existante
                List<Ligne_Reception> nouvellesLignesReceptions = receptionDataList.getLigneReceptions();
                System.out.println("nouvellesLignesRecettes: " + nouvellesLignesReceptions);

                // Associer les nouvelles lignes de recettes à la recette existante
                List<Ligne_Reception> ligneReceptions = recep.getLigneReceptions();
                System.out.println("ligneRecettes: " + ligneReceptions);

                ligneReceptions.addAll(nouvellesLignesReceptions);
                System.out.println("ligneRecettes après: " + ligneReceptions);

                // Associer la recette à chaque nouvelle ligne de recette
                for (Ligne_Reception ligneReception : nouvellesLignesReceptions) {
                    Produit produit = produitRepository.findById(ligneReception.getProduitId()).orElse(null);
                    ligneReception.setProduit(produit);
                    ligneReception.setBonreception(recep);
                }
                Double total=recep.getTotal_HT();
                System.out.println("cout avant ajout"+total);
                for (Ligne_Reception ligneReception : nouvellesLignesReceptions) {
                    total += ligneReception.getPrix_achat()*ligneReception.getQuantite_livree()- ligneReception.getMontant_remise(); // Ajoutez le coût de chaque produit à la recette
                }
                System.out.println("cout apres ajout"+total);
                recep.setTotal_HT(total);
                // Enregistrer les nouvelles lignes de recettes dans la base de données
                ligneReceptionRepository.saveAll(nouvellesLignesReceptions);
            }
            // Sauvegarder les modifications apportées à la recette
            receptionRepository.save(recep);
            System.out.println("la recette: " + recep);
        } catch (Exception e) {
            // Gérer les exceptions lors de la modification de la recette
        }

        return "redirect:/recep";
    }
    @RequestMapping("/lign/delete/{id}")
    public String deleteligne(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            System.out.println("\n\n\n\n ID =" + id + "\n\n\n\n\n");
            Ligne_Reception ligneReceptions = ligneReceptionRepository.findById(id).orElse(null);
            if (ligneReceptions != null) {
                // Récupérer la recette associée à la ligne de recette
                Bon_Reception bonreception = ligneReceptions.getBonreception();
                // Supprimer la ligne de recette
                ligneReceptionRepository.deleteById(id);

                // Mettre à jour le coût total de la recette
                Double coutTotal = 0.0;
                List<Ligne_Reception> ligneRecettes = bonreception.getLigneReceptions();
                for (Ligne_Reception ligne : ligneRecettes) {
                    coutTotal += ligne.getPrix_achat() * ligne.getQuantite_livree();
                }
                bonreception.setTotal_HT(coutTotal);
                receptionRepository.save(bonreception);
            }
            System.out.println("id ligne" + id);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "La ligne est liée à d'autres clés étrangères et ne peut pas être supprimée.");
        }

        return "redirect:/reception/create";
    }


}

