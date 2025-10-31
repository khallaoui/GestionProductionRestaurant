package com.example.gestionproductionrestaurant.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BonCommandeData {
    private BonCommande boncommande;
    private List<Ligne_Produit> ligneProduits;


}

