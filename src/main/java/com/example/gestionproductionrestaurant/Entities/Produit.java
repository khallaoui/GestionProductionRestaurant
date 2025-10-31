package com.example.gestionproductionrestaurant.Entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity @ToString
public class Produit {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nom;
    private String nomMarque;
    private String nomZone;
    private String unite;
    private Long numLot ;
    private Double tva;
    private  Long numLotAlert;
    private LocalDate dateExpiration;
    private LocalDate dateExpirationAlert;
    private Double QteLotAlert;
    private double stock_minimum;
    private double stock_maximum;
    private double stock_en_cours;
    private String code_barre;
    private boolean expiration;
    private double prix_initial;
    @Column(name = "description", columnDefinition = "text")
    private String description;
    private int nbr_jr_apres_ouverture ;
    private Long zoneId;
    private Long marqueId;
    @ManyToOne
    private Categorie categorie ;
    @ToString.Exclude
    @OneToMany(mappedBy = "produite",fetch = FetchType.EAGER)
    private List<Lot> lots;
    @ToString.Exclude
    @OneToMany(mappedBy = "produit",fetch = FetchType.EAGER)
    private List<Marque_Produit> marqueProduits;
    @ToString.Exclude
    @OneToMany(mappedBy = "produit")
    private List<Produit_Zone> produitZones;
    @ToString.Exclude
    @OneToMany(mappedBy = "produit")
    private List<Ligne_Recette> ligneRecettes;
    @ToString.Exclude
    @OneToMany(mappedBy = "produit")
    private List<Production> productions=new ArrayList<>();
    public boolean isExpiration() {
        return expiration;
    }
    public void setExpiration(boolean expiration) {
        this.expiration = expiration;
    }

    public List<Marque_Produit> getMarqueProduits() {
        return marqueProduits;
    }

    public void setMarqueProduits(List<Marque_Produit> marqueProduits) {
        this.marqueProduits = marqueProduits;
    }
}
