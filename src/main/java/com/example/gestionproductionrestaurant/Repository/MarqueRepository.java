package com.example.gestionproductionrestaurant.Repository;

import com.example.gestionproductionrestaurant.Entities.Marque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface MarqueRepository extends JpaRepository<Marque, Long> {

}

