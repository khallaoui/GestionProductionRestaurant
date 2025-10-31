package com.example.gestionproductionrestaurant.Repository;

import com.example.gestionproductionrestaurant.Entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser, Integer> {
    AppUser findByLogin (String login);
}
