package com.example.gestionproductionrestaurant.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Zone_stockage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ToString.Exclude
    @OneToMany(mappedBy = "zone_stockage")
    private List<Produit_Zone> produitZones;
    @NotEmpty(message = "La designation de la zone de stockage est obligatoire")

    private String designation;
}
