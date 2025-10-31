package com.example.gestionproductionrestaurant.Entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bon_Reception {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern ="yyyy-MM-dd")
    private Date date_livraison;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern ="yyyy-MM-dd")
    private Date date_commande;
    private long numero_bon;
    private String numero_bon_annee;
    // private double montant_TVA;
    private double total_HT;
    private double versement;

    @ManyToOne
    private Fournisseur fournisseur;

    @OneToMany
    private List<Ligne_Reception> ligneReceptions=new ArrayList<>();

    @Override
    public String toString() {
        return "Bon_Reception{" +
                "id=" + id +
                ", date_livraison=" + date_livraison +
                ", date_commande=" + date_commande +
                ", numero_bon=" + numero_bon +
                ", numero_bon_annee='" + numero_bon_annee + '\'' +
                ", total_HT=" + total_HT +
                ", versement=" + versement +
                '}';
    }
}
