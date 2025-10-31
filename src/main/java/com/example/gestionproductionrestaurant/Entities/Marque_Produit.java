package com.example.gestionproductionrestaurant.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Marque_Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;
    @ManyToOne
    @JoinColumn(name="Marque_id")
    private Marque marque;
    @ManyToOne
    @JoinColumn(name="produit_id")
    private Produit produit;


}
