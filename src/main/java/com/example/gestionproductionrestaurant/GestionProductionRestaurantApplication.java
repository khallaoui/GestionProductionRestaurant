package com.example.gestionproductionrestaurant;

import com.example.gestionproductionrestaurant.Entities.AppRole;
import com.example.gestionproductionrestaurant.Entities.AppUser;
import com.example.gestionproductionrestaurant.Metiers.SecuriteService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class GestionProductionRestaurantApplication {

    public static void main(String[] args) {
        ApplicationContext context =    SpringApplication.run(GestionProductionRestaurantApplication.class, args);




        SecuriteService secServ = context.getBean(SecuriteService.class); //String
        AppUser u1 = new AppUser("medo", "11121314");
        secServ.addUser(u1);


        AppUser u2 = new AppUser("khallaoui", "1234");
        secServ.addUser(u2);


        AppRole r1 = new AppRole("admin", "Le gerant");
        secServ.addRole(r1);

        AppRole r2 = new AppRole("user", "chef cuisinier");

        secServ.addRole(r2);

        secServ.addRoleToUser(r1, u1);
        secServ.addRoleToUser(r2, u2);

    }

}
