package com.example.gestionproductionrestaurant.Repository;

import com.example.gestionproductionrestaurant.Entities.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface FournisseurRepository extends JpaRepository<Fournisseur,Long> {

    @Query("SELECT COUNT(f) FROM Fournisseur f")
    long countTotalFournisseurs();

}

