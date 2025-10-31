package com.example.gestionproductionrestaurant.Repository;

import com.example.gestionproductionrestaurant.Entities.Production;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductionRepository extends JpaRepository<Production,Long> {
    List<Production> findByProduitId(Long productId);
}
