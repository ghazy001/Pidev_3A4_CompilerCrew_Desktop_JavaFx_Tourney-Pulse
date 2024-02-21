package edu.esprit.entities;

import java.util.Date;
import java.util.Objects;

public class Tournois {
    private int id_tournois;
    private String nom_tournois;
    private String address_tournois;
    private int nombre_match;
    private Date date_debut;
    private Date date_fin;

    public Tournois() {
    }

    public Tournois(String nom_tournois, String address_tournois, int nombre_match, Date date_debut, Date date_fin) {
        this.nom_tournois = nom_tournois;
        this.address_tournois = address_tournois;
        this.nombre_match = nombre_match;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
    }

    public Tournois(int id_tournois, String nom_tournois, String address_tournois, int nombre_match, Date date_debut, Date date_fin) {
        this.id_tournois = id_tournois;
        this.nom_tournois = nom_tournois;
        this.address_tournois = address_tournois;
        this.nombre_match = nombre_match;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
    }

    public int getId_tournois() {
        return id_tournois;
    }

    public void setId_tournois(int id_tournois) {
        this.id_tournois = id_tournois;
    }

    public String getNom_tournois() {
        return nom_tournois;
    }

    public void setNom_tournoi(String nom_tournois) {
        this.nom_tournois = nom_tournois;
    }

    public String getAddress_tournois() {
        return address_tournois;
    }

    public void setAddress_tournois(String address_tournois) {
        this.address_tournois = address_tournois;
    }

    public int getNombre_match() {
        return nombre_match;
    }

    public void setNombre_match(int nombre_match) {
        this.nombre_match = nombre_match;
    }

    public Date getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(Date date_debut) {
        this.date_debut = date_debut;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tournois tournois = (Tournois) o;
        return id_tournois == tournois.id_tournois && Objects.equals(nom_tournois, tournois.nom_tournois) && Objects.equals(address_tournois, tournois.address_tournois) && Objects.equals(nombre_match, tournois.nombre_match) && Objects.equals(date_debut, tournois.date_debut) && Objects.equals(date_fin, tournois.date_fin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_tournois, nom_tournois, address_tournois, nombre_match, date_debut, date_fin);
    }

    @Override
    public String toString() {
        return "tournois{" +
                "id_tournois=" + id_tournois +
                ", nom_tournoi='" + nom_tournois + '\'' +
                ", address_tournois='" + address_tournois + '\'' +
                ", nombre_match='" + nombre_match + '\'' +
                ", date_debut='" + date_debut + '\'' +
                ", date_fin='" + date_fin + '\'' +
                '}';
    }
}
