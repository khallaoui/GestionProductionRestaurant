package com.example.gestionproductionrestaurant.Entities;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
// for generate getters and setters
@AllArgsConstructor
@NoArgsConstructor
//for generate the constructor
public class Fournisseur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotEmpty(message = "Le nom du fournisseur est obligatoire")

    private String nom;
    @NotEmpty(message = "Le prenom du fournisseur est obligatoire")

    private String prenom;
    @NotNull(message = "Le téléphone du fournisseur est obligatoire")

    private Integer telephone;
    @NotEmpty(message = "La ville est obligatoire")

    private String ville;
    @NotEmpty(message = "Le code fournisseur est obligatoire")

    @Column(unique = true)
    private String code_Fournisseur;

    private int max_delai_livraison;
    @NotNull(message = "Le max delai pour le paiement est obligatoire")

    private Integer max_delai_Paiement;
        @NotNull(message = "Le max de la quantité recommander est obligatoire")

    private Double max_quantite_Recommandee;
    @Column(name = "description", columnDefinition = "text")
    private String description;
    private boolean principal;
    @NotNull(message = "Le min de la quantité recommander est obligatoire")

    private Double min_quantite_Recommandee;
    @ElementCollection
    @CollectionTable(name = "jours_disponibles")
    private List<String> joursDisponibles;

//    @OneToMany(mappedBy = "fournisseur")
//    private List<Jour_Disponible> jours_disponibles=new ArrayList<>();
//    @OneToMany(mappedBy = "fournisseur")
//    private List<JourDisponible> joursDisponibles  = new ArrayList<>();

    // public List<JourDisponible> getJoursDisponibles() {
    //   return joursDisponibles;
    //}

    //public void setJoursDisponibles(List<JourDisponible> joursDisponibles) {
    //  this.joursDisponibles = joursDisponibles;
    //}

    //public Fournisseur(List<JourDisponible> joursDisponibles) {
    //  this.joursDisponibles = joursDisponibles;
//    }
    //@ManyToMany
//@JoinTable(name = "disponibilite",
//        joinColumns = @JoinColumn(name = "fournisseur_id"),
//        inverseJoinColumns = @JoinColumn(name = "jour_id"))
//     private List<com.example.gestiondeproduction1.entities.Jour> joursDisponibles;

}
