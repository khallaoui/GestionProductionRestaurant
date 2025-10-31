package com.example.gestionproductionrestaurant.Entities;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Fournisseur_Produit {
    @Id
    private Double id;
    @ManyToOne
    @JoinColumn(name = "fournisseur_id")
    private Fournisseur fournisseur;
    @ManyToOne
    @JoinColumn(name = "produit_id")
    private Produit produit;


}
