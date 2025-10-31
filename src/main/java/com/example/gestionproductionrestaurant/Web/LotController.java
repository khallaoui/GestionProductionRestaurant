package com.example.gestionproductionrestaurant.Web;

import com.example.gestionproductionrestaurant.Entities.Lot;
import com.example.gestionproductionrestaurant.Entities.Produit;
import com.example.gestionproductionrestaurant.Metiers.LotMatier;
import com.example.gestionproductionrestaurant.Repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
public class LotController {

    @Autowired
    LotMatier lotMatier;
    @Autowired
    ProduitRepository produitRepository ;
  @GetMapping("/Lots")
    public String AfficherLot(Model model){
        System.out.println("aff");
        List<Lot> lots=lotMatier.AfficherLot();
        model.addAttribute("lots", lots);
        return "Lots";
    }
    @RequestMapping("/addLot")
    public String AjouterLot(Model model) {
        Lot lot = new Lot();
        model.addAttribute("lots", lot);
        return "addLot";
    }
    @RequestMapping(value = "/PostLot", method = RequestMethod.POST)
    public String postAjouterLot(@ModelAttribute("lots") Lot lot, Model model) {

        lotMatier.AjouterLot(lot);
        return "redirect:/Lots";
    }
    @RequestMapping("/Lot/delete/{id}")
    public String delete(@PathVariable Long id) {
        System.out.println("\n\n\n\n lot ="+id+"\n\n\n\n\n");
        lotMatier.deleteLot(id);
        return "redirect:/Lots";
    }
    @GetMapping("/Lot/new/{id}")
    public String editLotForm(@PathVariable Long id,Model model) {
        System.out.println("modif acces");
        model.addAttribute("lot",lotMatier.getLotById(id));
        return "EditLot";

    }
    @RequestMapping(value="/Lot/{id}")
    public String updateMarque(@PathVariable Long id,@ModelAttribute("lot") Lot lot, Model model) {
        System.out.println("gggg");
        Lot l =lotMatier.getLotById(id);
        l.setQte(lot.getQte());
        l.setNumero_Lot(lot.getNumero_Lot());
        l.setDate_expiration(lot.getDate_expiration());
        l.setDate_arrivee_lot(lot.getDate_arrivee_lot());
        l.setNbr_jr_avant_Expiration(lot.getNbr_jr_avant_Expiration());
        l.setDescription(lot.getDescription());
        lotMatier.modifierLot(l);
        return "redirect:/Lots";
    }

}

