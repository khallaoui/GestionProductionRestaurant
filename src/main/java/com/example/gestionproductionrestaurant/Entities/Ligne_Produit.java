package com.example.gestionproductionrestaurant.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ligne_Produit {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private Double qte_commandee;
    private Double prix_achat;
    private String unite;

    //private long id_marque;

    @ManyToOne
    @JoinColumn(name="id_produit")
    private Produit produit;

    @Transient
    private Long produitId;


    @ManyToOne
    @JoinColumn(name = "boncommande_id")
    private BonCommande bonCommande;


    //@ManyToOne
    //@JoinColumn(name="lot_id")
    //private Lot lot ;
    //@ManyToOne
    //@JoinColumn(name="marque_id")
    //private Marque marque ;
    //private String designation ;

}
