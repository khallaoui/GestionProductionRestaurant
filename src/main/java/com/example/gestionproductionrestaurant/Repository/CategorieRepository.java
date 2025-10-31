package com.example.gestionproductionrestaurant.Repository;

import com.example.gestionproductionrestaurant.Entities.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategorieRepository extends JpaRepository<Categorie,Long> {
    @Query("SELECT COUNT(c) FROM  Categorie  c ")
    long countTotalCategories();

}
