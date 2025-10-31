package com.example.gestionproductionrestaurant.Repository;

import com.example.gestionproductionrestaurant.Entities.Categorie_recette;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieRecetteRepository extends JpaRepository<Categorie_recette,Long> {
}

