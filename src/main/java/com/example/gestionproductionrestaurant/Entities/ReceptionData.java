package com.example.gestionproductionrestaurant.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceptionData {
    private Bon_Reception bon_reception;
    private List<Ligne_Reception> ligneReceptions;
}
