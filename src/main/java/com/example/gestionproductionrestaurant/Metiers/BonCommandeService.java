package com.example.gestionproductionrestaurant.Metiers;

import java.util.List;


import com.example.gestionproductionrestaurant.Entities.BonCommande;
import com.example.gestionproductionrestaurant.Entities.Fournisseur;
import com.example.gestionproductionrestaurant.Repository.BonCommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class BonCommandeService {

    @Autowired
    private BonCommandeRepository bonCommandeRepository;

    public List<BonCommande> listBonCommande() {
        return bonCommandeRepository.findAll();
    }

    public void AjouterBonCommande(BonCommande bonCommande) {
        bonCommandeRepository.save(bonCommande);


    }

    public Fournisseur getFournisseur() {
        return getFournisseur();
    }


    public BonCommande getBonCommandeById(Long id) {
        return bonCommandeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid BonCommande ID: " + id));
    }


}
