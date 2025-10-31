package com.example.gestionproductionrestaurant.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class AppRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 30)
    private String nomRole;
    private String description;
    public AppRole(String nomRole, String description) {
        super();
        this.nomRole = nomRole;
        this.description = description;
    }
    public AppRole() {
        super();
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNomRole() {
        return nomRole;
    }
    public void setNomRole(String nomRole) {
        this.nomRole = nomRole;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }



}
