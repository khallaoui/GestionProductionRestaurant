package com.example.gestionproductionrestaurant.Entities;
public enum Jour {
    LUNDI(0),
    MARDI(1),
    MERCREDI(2),
    JEUDI(3),
    VENDREDI(4),
    SAMEDI(5),
    DIMANCHE(6);

    private int index;

    Jour(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
