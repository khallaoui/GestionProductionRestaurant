package com.example.gestionproductionrestaurant.Web;


import com.example.gestionproductionrestaurant.Entities.AppUser;
import com.example.gestionproductionrestaurant.Repository.RoleRepository;
import com.example.gestionproductionrestaurant.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    @GetMapping("/login")
    public String getLogin(Model model) {
        AppUser user = new AppUser();
        model.addAttribute("user", user);
        //List<AppRole> appRoles=roleRepository.findAll();
        //model.addAttribute("appRoles",appRoles);
        System.out.println("------------ ICi login ----------------------- ");
        return "login";
    }




}

