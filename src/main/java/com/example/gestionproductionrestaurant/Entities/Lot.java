package com.example.gestionproductionrestaurant.Entities;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "La numéro de la lot est obligatoire")
    @Column(unique = true)
    private Long numero_Lot;
    @NotNull(message = "La date arrivee du lot designation de la lot est obligatoire")

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date_arrivee_lot;
    @NotNull(message = "La date d'expiration du lot designation de la lot est obligatoire")

    private LocalDate date_expiration;
    @NotNull(message = "La quantité de la lot est obligatoire")

    private Double qte;
    @NotNull(message = "le nombre du jour avant l'expiration de la lot est obligatoire")

    private int nbr_jr_avant_Expiration ;

    @Column(name = "description", columnDefinition = "text")
    private String description;
    @ManyToOne
    @JoinColumn(name = "produit_id")
    private Produit produite;

    /*@OneToMany(mappedBy = "lot",fetch = FetchType.LAZY)
    private List<Ligne_Produit> ligneProduits;*/
public Lot(Long num){
    this.setNumero_Lot(num);
}
}

