package com.example.gestionproductionrestaurant.Metiers;


import com.example.gestionproductionrestaurant.Entities.JourDisponible;
import com.example.gestionproductionrestaurant.Repository.JourDisponibleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JourDisponibleMetier {
    @Autowired
    private JourDisponibleRepository jourDisponibleRepository;

    public List<JourDisponible> afficherjourfour() {
        return jourDisponibleRepository.findAll();
    }


    public JourDisponible Ajouterjourfour(JourDisponible jr) {
        return jourDisponibleRepository.save(jr);
    }

    public JourDisponible getjourfourById(long id) {
        return jourDisponibleRepository.getById(id);
    }

}
