package com.example.gestionproductionrestaurant.Metiers;


import com.example.gestionproductionrestaurant.Entities.Fournisseur;
import com.example.gestionproductionrestaurant.Repository.FournisseurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class FournisseurMetier {
    @Autowired
    FournisseurRepository fournisseurRepository;


    public List<Fournisseur> afficherfournisseur() {
        return fournisseurRepository.findAll();
    }

    public Fournisseur AjouterFournisseur(Fournisseur fr) {

        return fournisseurRepository.save(fr);
    }

    public Fournisseur getFournisseurById(long id) {
        return fournisseurRepository.getById(id);
    }

    public void deleteFournisseur(Long id) {
        fournisseurRepository.deleteById(id);
    }

    public void modifierFournisseur(Fournisseur nouveaufour) {
        Optional<Fournisseur> ancienfour = fournisseurRepository.findById(nouveaufour.getId());
        Fournisseur fourn = ancienfour.get();
        fourn.setNom(nouveaufour.getNom());
        fourn.setVille(nouveaufour.getVille());
        fourn.setCode_Fournisseur(nouveaufour.getCode_Fournisseur());
        fourn.setMax_delai_livraison(nouveaufour.getMax_delai_livraison());
        fourn.setDescription(nouveaufour.getDescription());
        fourn.setPrenom(nouveaufour.getPrenom());
        fourn.setPrincipal(nouveaufour.isPrincipal());
        fourn.setMax_delai_Paiement(nouveaufour.getMax_delai_Paiement());
        fourn.setMax_quantite_Recommandee(nouveaufour.getMax_quantite_Recommandee());
        fourn.setTelephone(nouveaufour.getTelephone());
        fourn.setMin_quantite_Recommandee(nouveaufour.getMin_quantite_Recommandee());

        fournisseurRepository.save(fourn);
    }
}

