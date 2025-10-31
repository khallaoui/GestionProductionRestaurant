package com.example.gestionproductionrestaurant.Repository;


import com.example.gestionproductionrestaurant.Entities.BonCommande;
import com.example.gestionproductionrestaurant.Entities.Ligne_Produit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface Ligne_ProduitRepository extends JpaRepository<Ligne_Produit, Long> {
    List<Ligne_Produit> findByBonCommande(Optional<BonCommande> bonCommande);
    List<Ligne_Produit> findByBonCommande(BonCommande bonCommande);

}

