package com.example.gestionproductionrestaurant.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;

    @Entity
    @Data @NoArgsConstructor @AllArgsConstructor
    public class Production {
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;
        private String unite_entre;
        private String unite_sortie;
        private Double quantite_entre;
        private Double quantite_sortie;
        @Column(name = "description", columnDefinition = "text")
        private String description;
        //@Column(name = "temps", columnDefinition = "time")

        private LocalTime temps;
        private Float perte;
        private String titre;
        private Double prix;
        @DateTimeFormat(pattern ="yyyy-MM-dd HH:MM")
        private LocalDateTime date_heure_production;
        @ManyToOne
        private Produit produit;
        private long produitApres;
        @ManyToOne
        private Categorie categorie;

        public Production(String unite_entre, String unite_sortie, Double quantite_entre, Double quantite_sortie, String description, LocalTime temps, Float perte, String titre, LocalDateTime date_heure_production, Produit produit, Lot lot) {
            this.unite_entre = unite_entre;
            this.unite_sortie = unite_sortie;
            this.quantite_entre = quantite_entre;
            this.quantite_sortie = quantite_sortie;
            this.description = description;
            this.temps = temps;
            this.perte = perte;
            this.titre = titre;
            this.date_heure_production = date_heure_production;
            this.produit = produit;

        }
        @ManyToOne
        Laboratoire laboratoire;

    }


