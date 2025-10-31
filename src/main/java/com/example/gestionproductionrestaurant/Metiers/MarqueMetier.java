package com.example.gestionproductionrestaurant.Metiers;

import com.example.gestionproductionrestaurant.Entities.Marque;
import com.example.gestionproductionrestaurant.Repository.MarqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;
@Controller
public class MarqueMetier {
    @Autowired
    MarqueRepository marqueRepository;



    public List<Marque> AfficherMarque() {

        return marqueRepository.findAll();
    }

    public void AjouterMarque(Marque  M) {
        marqueRepository.save(M);
    }


    public void deleteMarque(Long id) {
        marqueRepository.deleteById(id);
    }
    public  Marque getMarqueById(Long id){
        return marqueRepository.getById(id);
    }
    public void modifierMarque(Marque nouveauMarque) {
        Optional<Marque> ancMarq = marqueRepository.findById(nouveauMarque.getId());
        Marque marque = ancMarq.get();
        marque.setLibelle(nouveauMarque.getLibelle());
        marque.setDescription(nouveauMarque.getDescription());
        marqueRepository.save(marque);
    }
}
