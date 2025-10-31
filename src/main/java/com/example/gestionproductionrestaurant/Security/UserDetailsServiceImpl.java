package com.example.gestionproductionrestaurant.Security;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.example.gestionproductionrestaurant.Entities.AppUser;
import com.example.gestionproductionrestaurant.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    private UserRepository userRepo;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> authorities = new ArrayList<>();
        AppUser user = userRepo.findByLogin(username);

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        user.getRoles().forEach(role ->{
            String roleName = role.getNomRole();
            SimpleGrantedAuthority sga = new SimpleGrantedAuthority(roleName);
            authorities.add(sga);
        });

        User u = new User(user.getLogin(), user.getMotDePasse(), authorities);

        return u;
    }

}
