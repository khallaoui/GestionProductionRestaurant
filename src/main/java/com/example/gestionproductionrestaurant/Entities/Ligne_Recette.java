package com.example.gestionproductionrestaurant.Entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class Ligne_Recette {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Double quantite;
    private String unite;
    //private double coutunite;
    //private double tva;

    @ManyToOne
    @JoinColumn(name="id_produit")
    private Produit produit;

    @ManyToOne
    @JoinColumn(name="id_recette")
    private Recette recette;
    @Transient
    private Long produitId;

}