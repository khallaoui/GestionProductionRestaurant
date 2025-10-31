package com.example.gestionproductionrestaurant.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
public class Categorie {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private   long id;
        @NotEmpty(message = "La Libelle de la catégorie est obligatoire")

        @Column(unique = true)
            private String Libelle;
        public Categorie(){

        }

}
