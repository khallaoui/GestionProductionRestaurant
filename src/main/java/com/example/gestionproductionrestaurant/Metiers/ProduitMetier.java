package com.example.gestionproductionrestaurant.Metiers;

import com.example.gestionproductionrestaurant.Entities.Produit;
import com.example.gestionproductionrestaurant.Repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProduitMetier {
    @Autowired
    private ProduitRepository produitRepository;

    public List<Produit> AfficherProduit() {
        return produitRepository.findAll();
    }
    //Ajouter un produit

    public void AjouterProduit(Produit P) {
        produitRepository.save(P);

    }

    public void ModifierProduit(Produit ProduitNouveau) {
        //trouver l'article
        Optional<Produit> ProduitAncien=produitRepository.findById(ProduitNouveau.getId());
        Produit produit=ProduitAncien.get();
        produit.setDescription(ProduitNouveau.getDescription());
        produit.setPrix_initial(ProduitNouveau.getPrix_initial());
        produit.setUnite(ProduitNouveau.getUnite());
        produit.setCode_barre(ProduitNouveau.getCode_barre());
        produit.setTva(ProduitNouveau.getTva());
        produit.setStock_en_cours(ProduitNouveau.getStock_en_cours());
        produit.setStock_maximum(ProduitNouveau.getStock_maximum());
        produit.setStock_minimum(ProduitNouveau.getStock_minimum());
        produit.setCategorie(ProduitNouveau.getCategorie());
        produit.setNom(ProduitNouveau.getNom());
        produit.setNbr_jr_apres_ouverture(ProduitNouveau.getNbr_jr_apres_ouverture());
        produit.setLots(ProduitNouveau.getLots());
        produit.setExpiration(ProduitNouveau.isExpiration());
        produitRepository.save(produit);

    }
    public Produit getSProduitByCode(Long id) {
        return produitRepository.getById(id);
    }

    public void deleteProduitById(Long id) {
        produitRepository.deleteById(id);

    }
}

