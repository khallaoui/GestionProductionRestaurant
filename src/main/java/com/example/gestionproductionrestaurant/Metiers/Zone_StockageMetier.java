package com.example.gestionproductionrestaurant.Metiers;

import com.example.gestionproductionrestaurant.Entities.Zone_stockage;
import com.example.gestionproductionrestaurant.Repository.Zone_stockageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class Zone_StockageMetier{

    @Autowired
    Zone_stockageRepository zoneStockageRepository;

    public List<Zone_stockage> AfficherZone_stockage() {

        return zoneStockageRepository.findAll();
    }
    public void AjouterZone_stockage(Zone_stockage ZS) {
        zoneStockageRepository.save(ZS);
    }


    public void deleteZone_stockage(Long id) {
        zoneStockageRepository.deleteById(id);
    }
    public  Zone_stockage getZone_stockageById(Long id){
        return zoneStockageRepository.getById(id);
    }
    public void modifierZone_stockage( Zone_stockage nouveauZone) {
        Optional<Zone_stockage> ancZone = zoneStockageRepository.findById(nouveauZone.getId());
        Zone_stockage Zone  = ancZone.get();
        Zone.setDesignation(nouveauZone.getDesignation());
        zoneStockageRepository.save(Zone);
    }
}
