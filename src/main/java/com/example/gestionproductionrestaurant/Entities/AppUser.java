package com.example.gestionproductionrestaurant.Entities;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 80)
    private String login;
    private String motDePasse;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<AppRole> roles = new ArrayList<>();

    public List<AppRole> getRoles() {
        return roles;
    }
    public void setRoles(List<AppRole> roles) {
        this.roles = roles;
    }
    public AppUser() {
    }
    public AppUser(String login, String motDePasse) {
        this.login = login;
        this.motDePasse = motDePasse;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getMotDePasse() {
        return motDePasse;
    }
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }


}
