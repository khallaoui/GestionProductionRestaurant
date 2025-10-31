package com.example.gestionproductionrestaurant.Metiers;

import com.example.gestionproductionrestaurant.Entities.Recette;
import com.example.gestionproductionrestaurant.Repository.LigneRecetteRepository;
import com.example.gestionproductionrestaurant.Repository.RecetteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
@Service
public class RecetteMetier {
    @Autowired
    RecetteRepository recetteRepository;
    @Autowired
    LigneRecetteRepository ligneRecetteRepository;
    public void modifierRecette(Recette nouveaurece) {
        Optional<Recette> ancienrece = recetteRepository.findById(nouveaurece.getId());
        Recette rece = ancienrece.get();
        rece.setDesignation(nouveaurece.getDesignation());
        rece.setDate_limite_consomation(nouveaurece.getDate_limite_consomation());
        rece.setCategorie_recette(nouveaurece.getCategorie_recette());
        recetteRepository.save(rece);
    }
}

