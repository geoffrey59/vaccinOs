package com.vaccinos.globals.models;

import com.google.firebase.Timestamp;

import java.util.Date;

public class Injection {
    private String ref;
    private String nomPatient;
    private String prenomPatient;
    private String maiPatient;
    private String telPatient;
    private String creneau;
    private String vaccin;
    private Timestamp dateDebut;
    private Timestamp dateFin;
    private Date dateInjection;
    private String medecin;


    public Injection(String nomPatient, String prenomPatient, String maiPatient, String telPatient, String creneau, String vaccin, Timestamp dateDebut, Timestamp dateFin, Date dateInjection, String medecin) {
        this.nomPatient = nomPatient;
        this.prenomPatient = prenomPatient;
        this.maiPatient = maiPatient;
        this.telPatient = telPatient;
        this.creneau = creneau;
        this.vaccin = vaccin;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.medecin = medecin;
        this.dateInjection = dateInjection;
    }
    public Injection(){

    }

    public String getNomPatient() {
        return nomPatient;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public void setNomPatient(String nomPatient) {
        this.nomPatient = nomPatient;
    }

    public String getPrenomPatient() {
        return prenomPatient;
    }

    public void setPrenomPatient(String prenomPatient) {
        this.prenomPatient = prenomPatient;
    }

    public String getMaiPatient() {
        return maiPatient;
    }

    public void setMaiPatient(String maiPatient) {
        this.maiPatient = maiPatient;
    }

    public String getTelPatient() {
        return telPatient;
    }

    public void setTelPatient(String telPatient) {
        this.telPatient = telPatient;
    }

    public String getCreneau() {
        return creneau;
    }

    public void setCreneau(String creneau) {
        this.creneau = creneau;
    }

    public String getVaccin() {
        return vaccin;
    }

    public void setVaccin(String vaccin) {
        this.vaccin = vaccin;
    }

    public Timestamp getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Timestamp dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Timestamp getDateFin() {
        return dateFin;
    }

    public void setDateFin(Timestamp dateFin) {
        this.dateFin = dateFin;
    }

    public String getMedecin() {
        return medecin;
    }

    public void setMedecin(String medecin) {
        this.medecin = medecin;
    }

    public Date getDateInjection() {
        return dateInjection;
    }

    public void setDateInjection(Date dateInjection) {
        this.dateInjection = dateInjection;
    }
}
