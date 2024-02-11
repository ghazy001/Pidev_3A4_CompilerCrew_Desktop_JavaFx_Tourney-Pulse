package edu.esprit.entities;

import java.util.Objects;

public class tournois {
    private int id_tournois;
    private String nom_tournoi;
    private String address_tournois;
    private String date_debut;
    private String date_fin;

    public tournois(String nom_tournoi, String address_tournois, String date_debut, String date_fin) {
        this.nom_tournoi = nom_tournoi;
        this.address_tournois = address_tournois;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
    }

    public tournois(int id_tournois, String nom_tournoi, String address_tournois, String date_debut, String date_fin) {
        this.id_tournois = id_tournois;
        this.nom_tournoi = nom_tournoi;
        this.address_tournois = address_tournois;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
    }

    public int getId_tournois() {
        return id_tournois;
    }

    public String getNom_tournoi() {
        return nom_tournoi;
    }

    public String getAddress_tournois() {
        return address_tournois;
    }

    public String getDate_debut() {
        return date_debut;
    }

    public String getDate_fin() {
        return date_fin;
    }

    public void setId_tournois(int id_tournois) {
        this.id_tournois = id_tournois;
    }

    public void setNom_tournoi(String nom_tournoi) {
        this.nom_tournoi = nom_tournoi;
    }

    public void setAddress_tournois(String address_tournois) {
        this.address_tournois = address_tournois;
    }

    public void setDate_debut(String date_debut) {
        this.date_debut = date_debut;
    }

    public void setDate_fin(String date_fin) {
        this.date_fin = date_fin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        tournois tournois = (tournois) o;
        return id_tournois == tournois.id_tournois && Objects.equals(nom_tournoi, tournois.nom_tournoi) && Objects.equals(address_tournois, tournois.address_tournois) && Objects.equals(date_debut, tournois.date_debut) && Objects.equals(date_fin, tournois.date_fin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_tournois, nom_tournoi, address_tournois, date_debut, date_fin);
    }

    @Override
    public String toString() {
        return "tournois{" +
                "id_tournois=" + id_tournois +
                ", nom_tournoi='" + nom_tournoi + '\'' +
                ", address_tournois='" + address_tournois + '\'' +
                ", date_debut='" + date_debut + '\'' +
                ", date_fin='" + date_fin + '\'' +
                '}';
    }
}

