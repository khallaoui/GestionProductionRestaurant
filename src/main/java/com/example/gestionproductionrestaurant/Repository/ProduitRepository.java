package com.example.gestionproductionrestaurant.Repository;

import com.example.gestionproductionrestaurant.Entities.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface ProduitRepository extends JpaRepository<Produit,Long> {
    @Query("SELECT COUNT(P) FROM  Produit P ")
    long countTotalProduits();

    @Query("SELECT DISTINCT p FROM Produit p " +
            "LEFT JOIN p.lots l " +
            "WHERE (:Aujour IS NULL OR l.date_expiration = CURRENT_DATE) " +
            "AND (:perime IS NULL OR l.date_expiration < CURRENT_DATE) " +
            "AND (:Semaine IS NULL OR DATEDIFF(l.date_expiration, CURRENT_DATE) BETWEEN 0 AND 7) " +
            "AND (:nombreJour IS NULL OR DATEDIFF(l.date_expiration, CURRENT_DATE) = :nombreJour)")
    List<Produit> rechercherProduitParMarqueZone(
            @Param("Aujour") Boolean Aujour,
            @Param("perime") Boolean perime,
            @Param("Semaine") Boolean Semaine,
            @Param("nombreJour") Integer nombreJour
    );
}

