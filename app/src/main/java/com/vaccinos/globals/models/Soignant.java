package com.vaccinos.globals.models;

public class Soignant {

    private String nomSoignant;
    private String prenomSoignant;

    public Soignant(String nomSoignant, String prenomSoignant) {
        this.nomSoignant = nomSoignant;
        this.prenomSoignant = prenomSoignant;
    }
    public Soignant(){

    }

    public String getNomSoignant() {
        return nomSoignant;
    }

    public void setNomSoignant(String nomSoignant) {
        this.nomSoignant = nomSoignant;
    }

    public String getPrenomSoignant() {
        return prenomSoignant;
    }

    public void setPrenomSoignant(String prenomSoignant) {
        this.prenomSoignant = prenomSoignant;
    }

    @Override
    public String toString() {
        return nomSoignant + " " + prenomSoignant;
    }
}
