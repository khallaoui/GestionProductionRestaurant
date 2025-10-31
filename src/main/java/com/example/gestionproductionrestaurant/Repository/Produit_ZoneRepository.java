package com.example.gestionproductionrestaurant.Repository;

import com.example.gestionproductionrestaurant.Entities.Produit;
import com.example.gestionproductionrestaurant.Entities.Produit_Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface Produit_ZoneRepository extends JpaRepository<Produit_Zone, Long> {
    @Query("SELECT DISTINCT pz FROM Produit p " +
            "LEFT JOIN p.marqueProduits mp " +
            "LEFT JOIN p.produitZones pz " +
            "LEFT JOIN p.categorie cat " +
            "LEFT JOIN p.lots l " +
            "WHERE (:ZoneId IS NULL OR pz.zone_stockage.id = :ZoneId) "+
            "or (:marqueId IS NULL OR mp.marque.id= :marqueId) " +
            "or (:categorie IS NULL OR cat.id = :categorie) "+
            "or (:date_expiration IS NULL OR l.date_expiration = :date_expiration) "+
            " or (LOWER(p.nom) LIKE LOWER(CONCAT('%', :nom, '%'))) "+
            " or (p.code_barre = :code_barre)")


    List<Produit_Zone> rechercherProduitParMarqueZone(
            @Param("ZoneId") Long ZoneId,
            @Param("categorie") Long categorie,
            @Param("code_barre") String code_barre,
            @Param("marqueId") Long marqueId,
            @Param("nom") String nom,
            @Param("date_expiration") LocalDate date_expiration
    );


}

