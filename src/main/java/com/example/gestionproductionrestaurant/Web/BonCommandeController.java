package com.example.gestionproductionrestaurant.Web;


import com.example.gestionproductionrestaurant.Entities.*;
import com.example.gestionproductionrestaurant.Metiers.BonCommandeService;
import com.example.gestionproductionrestaurant.Metiers.FournisseurMetier;
import com.example.gestionproductionrestaurant.Repository.BonCommandeRepository;
import com.example.gestionproductionrestaurant.Repository.FournisseurRepository;
import com.example.gestionproductionrestaurant.Repository.Ligne_ProduitRepository;
import com.example.gestionproductionrestaurant.Repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;


@Controller
public class BonCommandeController {

    @Autowired
    private FournisseurMetier fournisseurMetier;
    @Autowired
    private FournisseurRepository fournisseurRepository;

    @Autowired
    private Ligne_ProduitRepository ligne_ProduitRepository;

    @Autowired
    private BonCommandeRepository bonCommandeRepository;

    @Autowired
    private ProduitRepository produitRepository;


    @Autowired
    private BonCommandeService bonCommandeService;




    @GetMapping("/listbn")
    public String getBonCommande(Model model) {
        List<BonCommande> boncommande = bonCommandeRepository.findAll();
        List<Fournisseur>fournisseur=fournisseurRepository.findAll();
        model.addAttribute("boncommande", boncommande);
        model.addAttribute("fournisseur", fournisseur);
        System.out.println("----------- ici 3 listbn -------------");
        return "listboncommande";
    }

    @GetMapping("/addbon")
    public String ajouterBonCommande(Model model) {

        List<Produit> produits = produitRepository.findAll();
        Ligne_Produit lignproduit = new Ligne_Produit();

        model.addAttribute("fournisseur", fournisseurRepository.findAll());
        model.addAttribute("lignproduit", new Ligne_Produit());
        model.addAttribute("bonCommande", new BonCommande());
        model.addAttribute("listProduits", produits);

        System.out.println("----------- ici 1 addBonCommande -------------");
        return "ajouterboncommande";
    }

    @PostMapping("/boncommande/save")
    public String postAjouterBonCommande(@ModelAttribute("bonCommande") BonCommande bonCommande,
                                         @RequestBody List<BonCommandeData> bonCommandeDataList,
                                         RedirectAttributes redirectAttributes) {
        System.out.println("wa boooooooooooooooooooooooooooono " + bonCommandeDataList);
        for (BonCommandeData bonCommandeData : bonCommandeDataList) {
            Long fournisseurId = bonCommandeData.getBoncommande().getFournisseur().getId();
            System.out.println("fournisseurid" + fournisseurId);
            Fournisseur fournisseur = fournisseurMetier.getFournisseurById(fournisseurId);
            bonCommandeData.getBoncommande().setFournisseur(fournisseur);
            BonCommande savedBonCommande = bonCommandeRepository.save(bonCommandeData.getBoncommande());

            double coutTotal = 0.0;
            List<Ligne_Produit> ligneProduits = bonCommandeData.getLigneProduits();
            for (Ligne_Produit ligneProduit : ligneProduits) {
                Produit produit = produitRepository.findById(ligneProduit.getProduitId()).orElse(null);
                double coutUnite = ligneProduit.getPrix_achat();
                double quantite = ligneProduit.getQte_commandee();
                double PRIXligneProduit = coutUnite*quantite;
                coutTotal += PRIXligneProduit;

                ligneProduit.setBonCommande(savedBonCommande);
                ligneProduit.setProduit(produit);
                ligne_ProduitRepository.save(ligneProduit);
            }

            savedBonCommande.setPRIX_TOTAL(coutTotal);
            bonCommandeRepository.save(savedBonCommande);
        }
        redirectAttributes.addFlashAttribute("success", "La Bon de Commande a été enregistrée avec succès.");

        System.out.println("----------- ici  @PostMapping -------------");
        return "redirect:/addbon";

    }


    @PostMapping("/boncommande/delete/{id}")
    public String deleteBonCommande(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            bonCommandeRepository.deleteById(id);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "La boncommande est liée à d'autres clés étrangères et ne peut pas être supprimée.");
        }

        return "redirect:/listbn";
    }


    @RequestMapping("/lignepro/delete/{id}")
    public String deletelignepro(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            System.out.println("\n\n\n\n cat =" + id + "\n\n\n\n\n");
            Ligne_Produit ligne_Produit = ligne_ProduitRepository.findById(id).orElse(null);
            if (ligne_Produit != null) {
                BonCommande bonCommande = ligne_Produit.getBonCommande();
                ligne_ProduitRepository.deleteById(id);

                Double coutTotal = 0.0;
                List<Ligne_Produit> ligne_Produits = bonCommande.getLigne_Produit();
                for (Ligne_Produit ligne : ligne_Produits) {
                    coutTotal += ligne.getPrix_achat() * ligne.getQte_commandee();
                }
                bonCommande.setPRIX_TOTAL(coutTotal);
                bonCommandeRepository.save(bonCommande);
            }
            System.out.println("id ligne" + id);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "La ligne est liée à d'autres clés étrangères et ne peut pas être supprimée.");
        }

        return "redirect:/addbon";
    }


    @GetMapping("/boncommande/new/{id}")
    public String editboncommandeForm(Model model, @PathVariable Long id,RedirectAttributes redirectAttribute) {
        try{
            Optional<BonCommande> boncommande = bonCommandeRepository.findById(id);
            model.addAttribute("boncommande", boncommande);

            List<Ligne_Produit> ligne_Produits = ligne_ProduitRepository.findByBonCommande(boncommande);
            model.addAttribute("ligne_Produits", ligne_Produits);

            List<Fournisseur> fournisseur = fournisseurRepository.findAll();
            model.addAttribute("fournisseur",fournisseur);
            List<Produit> produits = produitRepository.findAll();
            model.addAttribute("produits", produits);
        }catch (Exception e){
            redirectAttribute.addFlashAttribute("error", e.getMessage());

        }
        return "editboncommande";

    }
    @RequestMapping(value ="/boncommande/{id}", method = RequestMethod.POST)
    public String updateeditboncommande(@PathVariable Long id, @RequestBody List<BonCommandeData> boncommandelist, Model model) {
        try {
            BonCommande boncommande1 = bonCommandeRepository.getById(id);
            if (!boncommandelist.isEmpty()) {
                BonCommandeData bonCommandeDataList = boncommandelist.get(0);
                BonCommande bonCommande = bonCommandeDataList.getBoncommande();

                boncommande1.setFournisseur(bonCommande.getFournisseur());
                boncommande1.setNumero_commande(bonCommande.getNumero_commande());

                List<Ligne_Produit> nouvellesLigne_Produits = bonCommandeDataList.getLigneProduits();
                System.out.println("nouvellesLigne_Produit: " + nouvellesLigne_Produits);

                List<Ligne_Produit> ligne_Produits = boncommande1.getLigne_Produit();
                System.out.println("ligne_Produits: " + ligne_Produits);

                ligne_Produits.addAll(nouvellesLigne_Produits);
                System.out.println("ligne_Produits après: " + ligne_Produits);

                // Associer la bonCommande à chaque nouvelle ligne_Produits
                for (Ligne_Produit ligne_Produit : nouvellesLigne_Produits) {
                    Produit produit = produitRepository.findById(ligne_Produit.getProduitId()).orElse(null);
                    ligne_Produit.setProduit(produit);
                    ligne_Produit.setBonCommande(boncommande1);
                }
                Double PRIX=boncommande1.getPRIX_TOTAL();
                System.out.println("PRIX avant ajout"+PRIX);
                for (Ligne_Produit ligne_Produit : nouvellesLigne_Produits) {
                    PRIX += ligne_Produit.getPrix_achat()*ligne_Produit.getQte_commandee();
                }
                System.out.println("PRIX apres ajout"+PRIX);
                boncommande1.setPRIX_TOTAL(PRIX);
                ligne_ProduitRepository.saveAll(nouvellesLigne_Produits);
            }
            bonCommandeRepository.save(boncommande1);
            System.out.println("la boncommande1: " + boncommande1);
        } catch (Exception e) {
        }

        return "redirect:/listbn";
    }


    @GetMapping("/bonrecu/new/{id}")
    public String recuForm(Model model, @PathVariable Long id,RedirectAttributes redirectAttribute) {
        try{
            BonCommande bonCommande = bonCommandeRepository.findById(id).orElse(null);
            model.addAttribute("bon_recep", bonCommande);

            List<Ligne_Produit> ligne_Produits = ligne_ProduitRepository.findByBonCommande(bonCommande);
            model.addAttribute("ligne_Produits", ligne_Produits);


            List<Produit> produits = produitRepository.findAll();
            model.addAttribute("produits", produits);
            // model.addAttribute("donneesList", donneesList);
        }catch (Exception e){
            redirectAttribute.addFlashAttribute("error", e.getMessage());

        }
        return "print";

    }


}
