package com.example.gestionproductionrestaurant.Web;
import com.example.gestionproductionrestaurant.Entities.*;
import com.example.gestionproductionrestaurant.Metiers.CategorieMetier;
import com.example.gestionproductionrestaurant.Metiers.LotMatier;
import com.example.gestionproductionrestaurant.Metiers.ProduitMetier;
import com.example.gestionproductionrestaurant.Metiers.Zone_StockageMetier;
import com.example.gestionproductionrestaurant.Repository.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.*;

@Controller
public class ProduitController {
    @Autowired
    private ProduitMetier produitMetier;
    @Autowired
    private ProduitRepository produitRepository;
    @Autowired
    private CategorieRepository categorieRepository;
    @Autowired
    private CategorieMetier categorieMetier;

    @Autowired
    private Zone_StockageMetier zoneStockageMetier;
    @Autowired
    private Zone_stockageRepository zoneStockageRepository;
    @Autowired
    MarqueRepository marqueRepository;
    @Autowired
    Marque_ProduitRepository marqueProduitRepository;
    @Autowired
    private LotMatier lotMatier;
    @Autowired
    private LotRepository lotRepository;
    @Autowired
    private Produit_ZoneRepository produitZoneRepository;
    // return view for add new Produit
    @GetMapping("/addProd")
    public String AddProduit(Model model) {
        Produit Prod = new Produit();
        Produit_Zone produitZone = new Produit_Zone();
        List<Categorie> categories = categorieRepository.findAll();
        List<Marque> marques = marqueRepository.findAll();
        List<Zone_stockage> Zones = zoneStockageRepository.findAll();
        model.addAttribute("Zones", Zones);
        model.addAttribute("categories", categories);
        model.addAttribute("marques", marques);
        model.addAttribute("Produits", Prod);
        model.addAttribute("ZoneProduit", produitZone);
        return "AjouterProduit";
    }

/*
    @PostMapping("/rechercheProduits")
   public String rechercherProduits(Model model,

                    @RequestParam(value = "marqueId", required = false) Long marqueId,
                    @RequestParam(value = "ZoneId", required = false) Long ZoneId ,
                    @RequestParam(value = "categorie", required = false) long categorie ,
                    @RequestParam(value = "nom", required = false) String nom,
                    @RequestParam(value = "code_barre", required = false) String code_barre)
      */
/*      @RequestParam(value = "lotId", required = false) Long lotId,
*//*

 {
     List<Produit> list =produitRepository.rechercherProduitParMarqueLotZone(
             ZoneId,categorie,code_barre,marqueId,nom );
     System.out.println("dkkkkkkkkkkkkkkkkkkkkk"+"idzone="+ZoneId+"categorie"+categorie+"codebarre"+code_barre+"marque"+marqueId+"nom"+nom );
     model.addAttribute("ZoneProduit", list);

        return "ResultChercher";
    }
*/

 /*   @PostMapping("/rechercheProduits")
    public String rechercherProduits(@RequestParam(value = "zoneId", required = false) Long zoneId, Model model) {
        List<Produit> produits = new ArrayList<>();

        if (zoneId != null ) {
            produits =l produitRepository.rechercherProduitsParZoneStockage(zoneId);
            System.out.println("idde zone"+produits);

        }
        model.addAttribute("produits", produits);
        return "resultatRecherche";
    }*/

   @GetMapping("/rechercheProduits")
    public String rechercherProduits(@RequestParam(value = "zoneId", required = false) Long zoneId,
                                     @RequestParam(value = "marqueId", required = false) Long marqueId,
                                     @RequestParam(value = "categorie", required = false) Long categorie ,
                                     @RequestParam(value = "code_barre", required = false) String code_barre ,
                                     @RequestParam(value = "nom", required = false) String nom,
                                     @RequestParam(value = "date_expiration", required = false)   LocalDate date_expiration,
                                     Model model) {

       List<Produit_Zone> produits = produitZoneRepository.rechercherProduitParMarqueZone(
               zoneId,
            categorie,
              code_barre,
              marqueId,
               nom,
               date_expiration
       );
        System.out.println("ddddddddddddddd"+date_expiration);
       List<Categorie> categori = categorieRepository.findAll();
       List<Marque> marques = marqueRepository.findAll();
       List<Zone_stockage> Zones = zoneStockageRepository.findAll();
       model.addAttribute("zoneStockageMetier", Zones);
       model.addAttribute("categories", categori);
       model.addAttribute("marques", marques);
       System.out.println("rppppppppppppoj"+produits);
       model.addAttribute("produits", produits);



       if (marqueId != null) {
               for (Produit_Zone pz : produits) {
                   if (pz != null) {
                       System.out.println("produit pz est " + pz);
                       Produit p = pz.getProduit();
                       for (Marque_Produit mp : p.getMarqueProduits()) {
                           if (mp.getMarque().getId() == marqueId) {
                               String marqueTest;
                               Marque marque = marqueRepository.findById(marqueId).orElse(null);
                               marqueTest = marque.getLibelle();
                               p.setNomMarque(marqueTest);
                           }
                       }
                   }
               }
           }
       if (zoneId != null) {
           for (Produit_Zone pz : produits) {
               if (pz != null) {
                   System.out.println("PZZZPZPZPZPZ" + pz);
                   Produit p;
                   p = pz.getProduit();
                   for (Produit_Zone pz1 : p.getProduitZones()) {
                       System.out.println("zzzzzzzzzzzzzzzzzzzzz" + pz1);
                       if (pz1.getZone_stockage().getId() == zoneId) {
                           String ZoneTesteTest;
                           Zone_stockage zoneStockage = zoneStockageRepository.findById(zoneId).orElse(null);
                           ZoneTesteTest = zoneStockage.getDesignation();
                           p.setNomZone(ZoneTesteTest);
                       }
                   }
               }
           }

       }
       if (date_expiration != null) {
           for (Produit_Zone pz : produits) {
               if (pz != null) {
                   Produit p = pz.getProduit();
                   for (Lot lot : p.getLots()) {
                       if ((lot.getDate_expiration()) != null) {
                           if ((lot.getDate_expiration()).equals(date_expiration)) {
                               LocalDate dateExpiration;
                               Long numLot;
                               numLot = lot.getNumero_Lot();
                               p.setNumLot(numLot);
                               dateExpiration = date_expiration;
                               p.setDateExpiration(dateExpiration);
                           }
                       }
                   }
               }
           }
       }
      /* if (marqueId != null) {
           for (Produit_Zone pz : produitZones) {
               Produit p = pz.getProduit();
               boolean marqueTrouvee = false;

               for (Marque_Produit mp : p.getMarqueProduits()) {
                   if (mp.getMarque().getId() == marqueId) {
                       marqueTrouvee = true;
                       break;
                   }
               }

               if (marqueTrouvee) {
                   Marque marque = marqueRepository.findById(marqueId).orElse(null);
                   model.addAttribute("marque", marque);
               } else {
                   model.addAttribute("nullMarque,", null);
               }
           }
       }
*/

       return "resultatRecherche";
   }

/*
        Zone_stockage zone = zoneStockageRepository.findById(zoneId).orElse(null);
        Marque marque = marqueRepository.findById(marqueId).orElse(null);

            if (zone != null || marque != null) {
                if (zone != null ) {
                    List<Produit_Zone> produitZones = zone.getProduitZones();
                    model.addAttribute("produitZones", produitZones);
                }
                if (marque != null) {
                    List<Marque_Produit> marqueProduits = marque.getMarqueProduits();
                    // Ajouter les produits et les quantités à l'objet Model
                    model.addAttribute("marqueProduits", marqueProduits);
                }
            }*/







  /*  public String rechercherProduitsParZone(@RequestParam("zoneId") Long zoneId, Model model) {
        List<Produit> produits = produitRepository.rechercherProduitsParZoneStockage(zoneId);
        Zone_stockage zone = zoneStockageRepository.findById(zoneId).orElse(null);

        model.addAttribute("produits", produits);
        model.addAttribute("zone", zone);
        System.out.println("voila la zone"+zone);

        return "resultatRecherche";
    }*/












/*    public String chercherProduit(Model model
    ,  @RequestParam(value = "zone", required = false) String zone,
                                  @RequestParam(value = "marque", required = false) String marque,
                                  @RequestParam(value = "libelle", required = false) String libelle,
                                  @RequestParam(value = "lot", required = false) String lot
    ) ) {
      *//*  List<Produit> produits = produitRepository.findAll();
        List<Categorie> categorie = categorieRepository.findAll();
        List<Marque> marques = marqueRepository.findAll();
        List<Zone_stockage> Zones = zoneStockageRepository.findAll();
        model.addAttribute("Zones", Zones);
        model.addAttribute("categories", categorie);
        model.addAttribute("marques", marques);
        model.addAttribute("listProduits", produits);*//*
        return "ListProduit";
    }*/
    //after remplissage the view on return forme with all the attributes
    @RequestMapping(value = "/PostaddProd", method = RequestMethod.POST)
    public String postAddProduit(HttpServletRequest request, @ModelAttribute("Produits") Produit produit, Model model,
                                @RequestParam("qte_Produit") List<Integer> quantite,@RequestParam("marqueId") List<Long> marqueIds,
                                 @RequestParam("ZoneId") List<Long> zoneIds) {
        {
//Categorie
            try {
                //categorie
                Long categorieId = produit.getCategorie().getId();
                Categorie categorie = categorieMetier.getcategorieById(categorieId);
                produit.setCategorie(categorie);
                Produit prodsaved = produitRepository.save(produit);

                //Zone
                for (int i = 0; i < zoneIds.size(); i++) {
                    Long zoneId = zoneIds.get(i);
                    Integer quantit = quantite.get(i); // Supposons que vous avez une liste 'quantites' contenant les quantités correspondantes

                    Produit_Zone produitZone = new Produit_Zone();
                    produitZone.setQte_Produit(quantit);
                    System.out.println("ID de zone sélectionnée : " + zoneId);
                    System.out.println("qte de zone sélectionnée : " + quantit);
                    Zone_stockage zone = zoneStockageRepository.findById(zoneId).orElse(null);
                    produitZone.setZone_stockage(zone);
                    produitZone.setProduit(prodsaved);
                    produitZoneRepository.save(produitZone);
                }
                //lot
                List<Lot> lots = prodsaved.getLots();
                System.out.println("yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy"+lots);
                for (Lot lot : lots) {
                    System.out.println("llllllllllllllllllllll"+lot);

                    lot.setProduite(prodsaved);
                    System.out.println("lllllllllllllppppppppppppp"+lot);

                    lotRepository.save(lot);

                }
//Marque
                for (Long marqueId : marqueIds) {
                    Marque marque = marqueRepository.findById(marqueId).orElse(null);
                    Marque_Produit marqueProduit = new Marque_Produit();
                    marqueProduit.setProduit(prodsaved);
                    marqueProduit.setMarque(marque);
                    marqueProduitRepository.save(marqueProduit);
                }
                return "redirect:/listProduits";
            } catch (Exception e) {

                e.printStackTrace(); // Affiche la trace de la pile de l'exception
                model.addAttribute("errorMessage", "Une erreur s'est produite lors de l'ajout du produit: " + e.getMessage());
                List<Produit> produits = produitRepository.findAll();
                model.addAttribute("produits", produits);

                return "redirect:/listProduits"; // Page listProduits avec message d'erreur affiché en haut
            }
        }

    }
    @GetMapping(path = "/listProduits")
    public String LitProduit(Model model) {
        List<Categorie> categorie = categorieRepository.findAll();
        List<Marque> marques = marqueRepository.findAll();
        List<Zone_stockage> Zones = zoneStockageRepository.findAll();
        model.addAttribute("zoneStockageMetier", Zones);
        model.addAttribute("categories", categorie);
        model.addAttribute("marques", marques);
        List<Produit> produits = produitRepository.findAll();
       model.addAttribute("listProduits", produits);
        return "ListProduit";
    }

    /*      List<Marque_Produit> marqueProduit=marqueProduitRepository.findAll();
 model.addAttribute("marqueProduit", marqueProduit);
*/



    @GetMapping("/Produit/new/{id}")
    public String editProduitForm(@PathVariable Long id,Model model) {
        Produit prod =produitMetier.getSProduitByCode(id);
        model.addAttribute("Produits",prod);
        List<Categorie> categories = categorieRepository.findAll();
        model.addAttribute("categories", categories);

        List<Zone_stockage> Zones = zoneStockageRepository.findAll();
        model.addAttribute("Zones", Zones);
       List<Produit_Zone>  produitZone = produitMetier.getSProduitByCode(id).getProduitZones();

        System.out.println("pppppppppppppppppp"+produitZone);
        System.out.println("nnnnnnnnnnnnnnnnnnnnn"+produitMetier.getSProduitByCode(id).getNom());
        model.addAttribute("ZoneProduit", produitZone);


         List<Lot> lotslist = new ArrayList<>() ;
        List<Lot> lots = lotRepository.findAll();
        for (Lot lo : lots) {

            if(lo.getProduite().getId()==id){
                lotslist.add(lo);
           }
        }
        model.addAttribute("lots", lotslist);
        return "ModifierProduit";

    }
    @RequestMapping(value="/Produit/{id}",method= RequestMethod.POST)
    public String updateSociete( @PathVariable Long id,@ModelAttribute("Produits") Produit produit ,  RedirectAttributes redirectAttributes,@ModelAttribute("lots") List<Lot> nouveauxLots) {
       try {

        Produit prod =produitMetier.getSProduitByCode((id));
        prod.setDescription(produit.getDescription());
        prod.setPrix_initial(produit.getPrix_initial());
        prod.setUnite(produit.getUnite());
        prod.setCode_barre(produit.getCode_barre());
        prod.setTva(produit.getTva());
        prod.setStock_en_cours(produit.getStock_en_cours());
        prod.setStock_maximum(produit.getStock_maximum());
        prod.setStock_minimum(produit.getStock_minimum());
        prod.setCategorie(produit.getCategorie());
        prod.setNom(produit.getNom());
        prod.setNbr_jr_apres_ouverture(produit.getNbr_jr_apres_ouverture());
        prod.setLots(produit.getLots());
        prod.setCategorie(produit.getCategorie());
        prod.setExpiration(produit.isExpiration());
        produitMetier.ModifierProduit(prod);

           List<Lot> lots = prod.getLots();
           System.out.println("yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy"+lots);
           for (int i = 0; i < nouveauxLots.size(); i++) {
               Lot lot = produit.getLots().get(i);
               Lot nouveauLot = nouveauxLots.get(i);
               lot.setNumero_Lot(nouveauLot.getNumero_Lot());
               lot.setDate_arrivee_lot(nouveauLot.getDate_arrivee_lot());
               lot.setDate_expiration(nouveauLot.getDate_expiration());
               lot.setQte(nouveauLot.getQte());
               lot.setNbr_jr_avant_Expiration(nouveauLot.getNbr_jr_avant_Expiration());
               lot.setDescription(nouveauLot.getDescription());
               lot.setProduite(prod);
               lotMatier.AjouterLot(lot);

           }

//Marque
        produitMetier.ModifierProduit(prod);
       } catch (Exception e) {
           e.printStackTrace(); // Affiche la trace de la pile de l'exception
           redirectAttributes.addFlashAttribute("error", "Une erreur s'est produite lors de la suppression du produit: " + e.getMessage());
       }

        return "redirect:/listProduits";
    }
    @RequestMapping("/Produit/delete/{id}")
    public String deleteProduit(@PathVariable long id, RedirectAttributes redirectAttributes) {
        try {
            produitMetier.deleteProduitById(id);
        } catch (Exception e) {
            e.printStackTrace(); // Affiche la trace de la pile de l'exception
            redirectAttributes.addFlashAttribute("error", "Une erreur s'est produite lors de la suppression du produit: " + e.getMessage());
        }

        return "redirect:/listProduits";
    }

    @RequestMapping(value = "/ResultAlert", method = RequestMethod.GET)
    public String rechercherProduits(
            @RequestParam(value = "aler", required = false) String aler,
            @RequestParam(value = "nombreJour", required = false) Integer nombreJour,
            Model model
    ) {
        List<Produit> produits = new ArrayList<>();

        if (aler != null) {
            if (aler.equals("Aujour")) {
               System.out.println("alahaah");
                produits = produitRepository.rechercherProduitParMarqueZone(true, null, null, null);
                    for (Produit pr : produits) {
                        for (Lot lot : pr.getLots()) {
                            if ((lot.getDate_expiration()) != null) {
                                pr.setNumLotAlert( lot.getNumero_Lot());
                                pr.setDateExpirationAlert( lot.getDate_expiration());
                                pr.setQteLotAlert( lot.getQte());

                                }
                            }
                        }

                model.addAttribute("produits", produits);
            } else if (aler.equals("perime")) {
                produits = produitRepository.rechercherProduitParMarqueZone(null, true, null, null);
                for (Produit pr : produits) {
                    for (Lot lot : pr.getLots()) {
                        if ((lot.getDate_expiration()) != null) {
                            pr.setNumLotAlert( lot.getNumero_Lot());
                            pr.setDateExpirationAlert( lot.getDate_expiration());
                            pr.setQteLotAlert( lot.getQte());

                        }
                    }
                }
                model.addAttribute("produits", produits);

            } else if (aler.equals("Semaine")) {
                produits = produitRepository.rechercherProduitParMarqueZone(null, null, true, null);
                for (Produit pr : produits) {
                    for (Lot lot : pr.getLots()) {
                        if ((lot.getDate_expiration()) != null) {
                            pr.setNumLotAlert( lot.getNumero_Lot());
                            pr.setDateExpirationAlert( lot.getDate_expiration());
                            pr.setQteLotAlert( lot.getQte());

                        }
                    }
                }
                model.addAttribute("produits", produits);

            }
        } /*else if (nombreJour != null) {
            produits = produitRepository.rechercherProduitParMarqueZone(null, null, null, nombreJour);
            model.addAttribute("produits", produits);
*/

        return "TableAlert";
    }


    @GetMapping(path = "/listProduitsAlert")
    public String LitProduitAlert(Model model) {
       return "TableAlert";
    }
    
    @GetMapping(path = "/PlusDetail")
    public String PlusDetail(Model model) {
       return "PlusDetails";
    }
}

