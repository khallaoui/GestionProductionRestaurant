package com.example.gestionproductionrestaurant.Metiers;


import com.example.gestionproductionrestaurant.Entities.Bon_Reception;
import com.example.gestionproductionrestaurant.Repository.LigneReceptionRepository;
import com.example.gestionproductionrestaurant.Repository.ReceptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReceptionMetier {
    @Autowired
    ReceptionRepository receptionRepository;
    @Autowired
    LigneReceptionRepository ligneReceptionRepository;


    public void modifierReception(Bon_Reception nouveaurece) {
        Optional<Bon_Reception> ancienrece = receptionRepository.findById(nouveaurece.getId());
//        Optional<Ligne_Reception> ancienligne = ligneReceptionRepository.findById(nouveauligne.getId());
//        Ligne_Reception ligneReception = ancienligne.get();
        Bon_Reception bonrecep = ancienrece.get();
        bonrecep.setDate_commande(nouveaurece.getDate_commande());
        bonrecep.setDate_livraison(nouveaurece.getDate_livraison());
        bonrecep.setNumero_bon(nouveaurece.getNumero_bon());
        bonrecep.setTotal_HT(nouveaurece.getTotal_HT());
        bonrecep.setVersement(nouveaurece.getVersement());
//        ligneRecette.setUnite(nouveauligne.getUnite());
//        ligneRecette.setCout_unite(nouveauligne.getCout_unite());
//        ligneRecette.setTva(nouveauligne.getTva());
//        ligneRecette.setQuantite(nouveauligne.getQuantite());

        receptionRepository.save(bonrecep);
    }
    public Bon_Reception getrecuById(long id){
        return receptionRepository.getById(id);
    }



}

