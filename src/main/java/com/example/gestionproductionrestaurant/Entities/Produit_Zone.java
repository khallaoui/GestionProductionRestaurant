package com.example.gestionproductionrestaurant.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Produit_Zone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int qte_Produit;
    @ManyToOne
    @JoinColumn(name="id_produit")
    private Produit produit;

    @ManyToOne
    @JoinColumn(name="id_zone_stockage")
    private Zone_stockage zone_stockage ;
    public int getQte_Produit() {
        return qte_Produit;
    }
    public Produit getProduit() {
        return produit;
    }
    public String getNom() {
        return produit.getNom();
    }
    public String getCodebarre() {
        return produit.getCode_barre();
    }
    public void setProduit(Produit produit) {
        this.produit = produit;
    }
}
