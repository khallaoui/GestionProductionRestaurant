package com.example.gestionproductionrestaurant.Repository;

import com.example.gestionproductionrestaurant.Entities.Recette;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecetteRepository extends JpaRepository<Recette,Long> {
}
