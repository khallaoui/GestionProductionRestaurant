package com.example.gestionproductionrestaurant.Repository;


import com.example.gestionproductionrestaurant.Entities.Bon_Reception;
import com.example.gestionproductionrestaurant.Entities.Ligne_Reception;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LigneReceptionRepository extends JpaRepository<Ligne_Reception, Long> {
    List<Ligne_Reception> findByBonreception(Bon_Reception bon_reception);



}
