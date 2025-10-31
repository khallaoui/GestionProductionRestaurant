package com.example.gestionproductionrestaurant.Repository;

import com.example.gestionproductionrestaurant.Entities.Ligne_Recette;
import com.example.gestionproductionrestaurant.Entities.Recette;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LigneRecetteRepository extends JpaRepository<Ligne_Recette,Long> {
    List<Ligne_Recette> findByRecette(Optional<Recette> recette);
}
