package edu.esprit.entities;

import java.util.Objects;
import edu.esprit.entities.tournois;

public class matchs {

    private int id_match;
    private String nom_match;
    private String date_match;
    private String duree_match;
    private tournois id_tournois;


    public matchs(String nom_match, String date_match, String duree_match, tournois id_tournois) {
        this.nom_match = nom_match;
        this.date_match = date_match;
        this.duree_match = duree_match;
        this.id_tournois = id_tournois;
    }

    public matchs(int id_match, String nom_match, String date_match, String duree_match, tournois id_tournois) {
        this.id_match = id_match;
        this.nom_match = nom_match;
        this.date_match = date_match;
        this.duree_match = duree_match;
        this.id_tournois = id_tournois;
    }

    public int getId_match() {
        return id_match;
    }

    public void setId_match(int id_match) {
        this.id_match = id_match;
    }

    public String getNom_match() {
        return nom_match;
    }

    public void setNom_match(String nom_match) {
        this.nom_match = nom_match;
    }

    public String getDate_match() {
        return date_match;
    }

    public void setDate_match(String date_match) {
        this.date_match = date_match;
    }

    public String getDuree_match() {
        return duree_match;
    }

    public void setDuree_match(String duree_match) {
        this.duree_match = duree_match;
    }

    public tournois getId_tournois() {
        return id_tournois;
    }

    public void setId_tournois(tournois id_tournois) {
        this.id_tournois = id_tournois;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        matchs matchs = (matchs) o;
        return id_match == matchs.id_match && Objects.equals(nom_match, matchs.nom_match) && Objects.equals(date_match, matchs.date_match) && Objects.equals(duree_match, matchs.duree_match) && Objects.equals(id_tournois, matchs.id_tournois);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_match, nom_match, date_match, duree_match, id_tournois);
    }

    @Override
    public String toString() {
        return "matchs{" +
                "id_match=" + id_match +
                ", nom_match='" + nom_match + '\'' +
                ", date_match='" + date_match + '\'' +
                ", duree_match='" + duree_match + '\'' +
                ", id_tournois=" + id_tournois +
                '}';
    }
}
