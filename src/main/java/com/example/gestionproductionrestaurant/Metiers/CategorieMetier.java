package com.example.gestionproductionrestaurant.Metiers;


import com.example.gestionproductionrestaurant.Entities.Categorie;
import com.example.gestionproductionrestaurant.Repository.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategorieMetier {
    @Autowired
    CategorieRepository categorieRepository;

    public List<Categorie> AfficherCategorie() {

        return categorieRepository.findAll();
    }

    public void AjouterCategorie(Categorie c) {
        categorieRepository.save(c);
    }
    public void deletecat(Long id) {
        categorieRepository.deleteById(id);
    }
    public  Categorie getcategorieById(Long id){
        return categorieRepository.getById(id);
    }
    public void modifiercate(Categorie nouveaucat) {
        Optional<Categorie> anciencat = categorieRepository.findById(nouveaucat.getId());
        Categorie cat = anciencat.get();
        cat.setLibelle(nouveaucat.getLibelle());
        categorieRepository.save(cat);
    }
}