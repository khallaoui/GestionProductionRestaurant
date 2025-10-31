package com.example.gestionproductionrestaurant.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BonCommande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int numero_commande;
    private double PRIX_TOTAL;

    @DateTimeFormat(pattern ="yyyy-MM-dd")
    private Date dateCreation;

    private String numero_bon_annee;
    private static int currentYear;
    private static int counter;

    @ManyToOne
    private Fournisseur fournisseur;

    @OneToMany(mappedBy = "bonCommande",fetch = FetchType.LAZY)
    private List<Ligne_Produit> ligne_Produit;

    @PrePersist
    private void generateNumeroBonAnnee() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int year = calendar.get(Calendar.YEAR);

        if (year != currentYear) {
            currentYear = year;
            counter = 1;
        }

        numero_bon_annee = String.format("%d/%d", counter, currentYear);
        counter++;
        this.dateCreation = new Date();
    }

    @Override
    public String toString() {
        return "BonCommande [id=" + id + ", numero_commande=" + numero_commande + ", PRIX_TOTAL=" + PRIX_TOTAL
                + ", dateCreation=" + dateCreation + ", numero_bon_annee=" + numero_bon_annee + "]";
    }


}


