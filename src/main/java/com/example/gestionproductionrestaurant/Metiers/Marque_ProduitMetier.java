package com.example.gestionproductionrestaurant.Metiers;

import com.example.gestionproductionrestaurant.Entities.Marque;
import com.example.gestionproductionrestaurant.Entities.Marque_Produit;
import com.example.gestionproductionrestaurant.Repository.MarqueRepository;
import com.example.gestionproductionrestaurant.Repository.Marque_ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service

public class Marque_ProduitMetier {
    @Autowired
    Marque_ProduitRepository marqueProduitRepository;



    public List<Marque_Produit> AfficherMarque() {

        return marqueProduitRepository.findAll();
    }

    public void AjouterMarqueProduit(Marque_Produit  MP) {
        marqueProduitRepository.save(MP);
    }


    public void deleteMarqueProduit(Long id) {
        marqueProduitRepository.deleteById(id);
    }
    public  Marque_Produit getMarqueProduitById(Long id){
        return marqueProduitRepository.getById(id);
    }


}
