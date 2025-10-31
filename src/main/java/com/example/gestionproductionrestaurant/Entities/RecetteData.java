package com.example.gestionproductionrestaurant.Entities;

import lombok.*;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecetteData {
    private Recette recette;
    private List<Ligne_Recette> ligneRecettes;


}
