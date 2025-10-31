package com.example.gestionproductionrestaurant.Repository;

import com.example.gestionproductionrestaurant.Entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<AppRole, Integer> {
    Optional<AppRole> findByNomRole(String nomRole);

}
