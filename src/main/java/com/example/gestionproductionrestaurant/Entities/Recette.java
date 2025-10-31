package com.example.gestionproductionrestaurant.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recette {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
//    @NotEmpty(message = "La designation de la recette est obligatoire")
    @Column(unique = true)
    private String designation;

/*
    @NotNull(message = "La cout total de la recette est obligatoire")
*/
    private Double cout_total;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern ="yyyy-MM-dd")
    private Date date_limite_consomation;

/*
    @NotNull(message = "La categorie de la recette est obligatoire")
*/
    @ManyToOne
    @JoinColumn(name = "categorie_recette_id")
    private Categorie_recette categorie_recette;
    @OneToMany(mappedBy = "recette",  cascade = CascadeType.ALL)
    private List<Ligne_Recette> ligneRecettes=new ArrayList<>();
    @Override
    public String toString() {
        return "Recette [id=" + id + ", designation=" + designation + ", date_limite_consomation=" + date_limite_consomation
                + ", categorie_recette=" + categorie_recette + "]";
    }

}
