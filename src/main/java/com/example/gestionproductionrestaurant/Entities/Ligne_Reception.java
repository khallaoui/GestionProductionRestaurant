package com.example.gestionproductionrestaurant.Entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor


public class Ligne_Reception {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /*  @ManyToOne
      @JoinColumn(name = "bonreception_id")
      private Bon_Reception bon_reception;*/
    @ManyToOne
    @JoinColumn(name = "bonreception_id")
    private Bon_Reception bonreception;



    @ManyToOne
    @JoinColumn(name = "produit_id")
    private Produit produit;


    private double quantite_livree;
    private double prix_achat;
    private double montant_remise;
    //    private double montant_TVA;
    // private long id_marque;
    private String unite;

    @Transient
    private Long produitId;

}


