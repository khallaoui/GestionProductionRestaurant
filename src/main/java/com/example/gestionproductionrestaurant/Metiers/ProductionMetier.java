package com.example.gestionproductionrestaurant.Metiers;

import com.example.gestionproductionrestaurant.Entities.Production;
import com.example.gestionproductionrestaurant.Repository.ProductionRepository;
import com.example.gestionproductionrestaurant.Repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductionMetier {
    @Autowired
    private ProductionRepository productionRepository;
    @Autowired
    private ProduitRepository produitRepository;

    public void ajouterProduction(Production p) {
        productionRepository.save(p);
    }

    public Optional<Production> AfficherProduction(long id) {
        return productionRepository.findById(id);
    }

    public Production getProductionByCode(long id) {
        return productionRepository.getById(id);
    }

    public void deleteProductionById(long id) {
        productionRepository.deleteById(id);
    }

    public void modifierprod(Production newproduction) {

        Optional<Production> productionancien = productionRepository.findById(newproduction.getId());
        Production prd = productionancien.get();
        prd.setUnite_entre(newproduction.getUnite_entre());
        prd.setUnite_sortie(newproduction.getUnite_sortie());
        prd.setQuantite_entre(newproduction.getQuantite_entre());
        prd.setQuantite_sortie(newproduction.getQuantite_sortie());
        prd.setDescription(newproduction.getDescription());
        prd.setPerte(newproduction.getPerte());
        prd.setTitre(newproduction.getTitre());
        prd.setDate_heure_production(newproduction.getDate_heure_production());
        //prd.setCategorie_recette(newproduction.getCategorie_recette());
        productionRepository.save(prd);
    }
    public List<Production> getProductionsByProduct(Long productId) {
        return productionRepository.findByProduitId(productId);
    }


}

