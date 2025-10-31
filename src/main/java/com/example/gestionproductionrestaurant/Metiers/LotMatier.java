package com.example.gestionproductionrestaurant.Metiers;

import com.example.gestionproductionrestaurant.Entities.Lot;
import com.example.gestionproductionrestaurant.Repository.LotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class LotMatier {
    @Autowired
    LotRepository lotRepository;
    public List<Lot> AfficherLot() {

        return lotRepository.findAll();
    }
    public void AjouterLot(Lot  L) {
        lotRepository.save(L);
    }
    public void deleteLot(Long id) {
        lotRepository.deleteById(id);
    }
    public  Lot getLotById(Long id){
        return lotRepository.getById(id);
    }
    public void modifierLot(Lot nouveauLot) {
        Optional<Lot> ancLot= lotRepository.findById(nouveauLot.getId());
        Lot lot = ancLot.get();
        lot.setNumero_Lot(nouveauLot.getNumero_Lot());
        lot.setDate_arrivee_lot(nouveauLot.getDate_arrivee_lot());
        lot.setDate_expiration(nouveauLot.getDate_expiration());
        lot.setNbr_jr_avant_Expiration(nouveauLot.getNbr_jr_avant_Expiration());
        lot.setQte(nouveauLot.getQte());
        lot.setDescription(nouveauLot.getDescription());
        lot.setDescription(nouveauLot.getDescription());
        lotRepository.save(lot);
    }
}
