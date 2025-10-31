package com.example.gestionproductionrestaurant.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Marque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;
    @NotEmpty(message = "La libelle de la marque est obligatoire")
    @Column(unique = true)
    private String libelle;
    @Column(name = "description", columnDefinition = "text")
    private String description;
//    @ToString.Exclude
//    @OneToMany(mappedBy = "marque",fetch = FetchType.LAZY)
//    private List<Ligne_Produit> ligneProduits;
    @ToString.Exclude
    @OneToMany(mappedBy = "marque",fetch = FetchType.EAGER)
    private List<Marque_Produit> marqueProduits;

}
