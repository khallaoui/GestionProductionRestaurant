package com.example.gestionproductionrestaurant.Metiers;

import com.example.gestionproductionrestaurant.Entities.Laboratoire;
import com.example.gestionproductionrestaurant.Repository.LaboratoireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LaboratoireMetier {
    @Autowired
    LaboratoireRepository laboratoireRepository;

    public List<Laboratoire> AfficherLaboratoire() {

        return laboratoireRepository.findAll();
    }

    public void AjouterLaboratoire(Laboratoire l) {
        laboratoireRepository.save(l);
    }
    public void deleteclab(Long id) {
        laboratoireRepository.deleteById(id);
    }
    public  Laboratoire getlaboratoireById(Long id){
        return laboratoireRepository.getById(id);
    }
    public void modifierlab(Laboratoire nouveaulab) {
        Optional<Laboratoire> ancienlab = laboratoireRepository.findById(nouveaulab.getId());
        Laboratoire lab = ancienlab.get();
        lab.setNom(nouveaulab.getNom());
        laboratoireRepository.save(lab);
    }
}
