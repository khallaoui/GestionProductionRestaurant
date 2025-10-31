package com.example.gestionproductionrestaurant.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "jours_disponibles")
public class JourDisponible {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "fournisseur_id")
    private Fournisseur fournisseur;

//    @Enumerated(EnumType.STRING)
//    private Jour jour;


//    @ManyToOne
//    @JoinColumn(name = "fournisseur_id")
//    private Fournisseur fournisseur;

//    public String getCodeJour() {
//        return codeJour;
//    }
//
//    public void setCodeJour(String codeJour) {
//        this.codeJour = codeJour;
//    }
}
