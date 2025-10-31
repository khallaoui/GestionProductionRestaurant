package com.example.gestionproductionrestaurant.Metiers;


import java.util.ArrayList;
import java.util.List;

import com.example.gestionproductionrestaurant.Entities.AppRole;
import com.example.gestionproductionrestaurant.Entities.AppUser;
import com.example.gestionproductionrestaurant.Repository.RoleRepository;
import com.example.gestionproductionrestaurant.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class SecuriteService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private RoleRepository roleRepo;

    public void addUser(AppUser u) {
        u.setMotDePasse(passwordEncoder.encode(u.getMotDePasse()));
        userRepo.save(u);
    }

    public void addRole(AppRole r) {
        roleRepo.save(r);
    }

    public boolean addRoleToUser(AppRole r, AppUser u) {
        List<AppRole> roles_u = u.getRoles();

        if(roles_u.contains(r))
            return false;
        //throw new RuntimeException("Le role existe déjà !!!!");

        roles_u.add(r);
        userRepo.save(u);
        return true;
    }

    public boolean removeRoleFormUser(AppRole r, AppUser u) {
        List<AppRole> roles_u = u.getRoles();
        if(!roles_u.isEmpty()) {
            if(roles_u.contains(r)) {
                roles_u.remove(r);
                userRepo.save(u);
                return true;
            }
        }
        return false;
    }

}
