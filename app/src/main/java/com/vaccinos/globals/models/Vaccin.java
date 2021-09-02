package com.vaccinos.globals.models;

public class Vaccin {

    private String name;
    private int stock;
    private int nbDoseParFlacon;
    private int nbDoseUtilise;
    private int nbDeFlacon;


    public Vaccin(String nom, int stock, int nbDoseParFlacon, int nbDoseUtilise, int nbDeFlacon) {
        this.name = nom;
        this.stock = stock;
        this.nbDoseParFlacon = nbDoseParFlacon;
        this.nbDoseUtilise = nbDoseUtilise;
        this.nbDeFlacon = nbDeFlacon;
    }
    public Vaccin(){

    }

    public String getName() {
        return name;
    }

    public void setName(String nom) {
        this.name = nom;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getNbDoseParFlacon() {
        return nbDoseParFlacon;
    }

    public void setNbDoseParFlacon(int nbDoseParFlacon) {
        this.nbDoseParFlacon = nbDoseParFlacon;
    }

    public int getNbDoseUtilise() {
        return nbDoseUtilise;
    }

    public void setNbDoseUtilise(int nbDoseUtilise) {
        this.nbDoseUtilise = nbDoseUtilise;
    }

    public int getNbDeFlacon() { return nbDeFlacon; }

    public void setNbDeFlacon(int nbDeFlacon) { this.nbDeFlacon = nbDeFlacon;}
}
