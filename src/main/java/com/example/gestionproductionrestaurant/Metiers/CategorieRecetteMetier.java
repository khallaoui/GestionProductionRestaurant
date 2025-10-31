package com.example.gestionproductionrestaurant.Metiers;


import com.example.gestionproductionrestaurant.Entities.Categorie_recette;
import com.example.gestionproductionrestaurant.Repository.CategorieRecetteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategorieRecetteMetier {
    @Autowired
    CategorieRecetteRepository categorieRecetteRepository;
    public void modifiercate(Categorie_recette nouveaucat) {
        Optional<Categorie_recette> anciencat = categorieRecetteRepository.findById(nouveaucat.getId());
        Categorie_recette cat = anciencat.get();
        cat.setLibelle(nouveaucat.getLibelle());
        categorieRecetteRepository.save(cat);
    }
    public Categorie_recette getcategorierecetById(long id){
        return categorieRecetteRepository.getById(id);
    }
}
