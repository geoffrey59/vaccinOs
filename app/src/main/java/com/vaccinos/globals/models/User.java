package com.vaccinos.globals.models;

public class User {
    private String ref;
    private String nom;
    private String prenom;
    private String email;
    private boolean isDelete;

    public User(String ref,String nom, String prenom, String email, boolean isDelete) {
        this.ref = ref;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.isDelete = isDelete;
    }
    public User(){

    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean isDelete) {
        isDelete = isDelete;
    }
}
