package edu.esprit.entities;

import java.util.Date;
import java.util.Objects;

public class Matchs {

    private int id_match;
    private String nom_match;
    private Date date_match;
    private String duree_match;
    private Tournois tournois;
    private Equipe equipe;
    private Equipe equipe1;


    public Matchs() {
    }

    public Matchs(String nom_match, Date date_match, String duree_match, Tournois tournois, Equipe equipe, Equipe equipe1) {
        this.nom_match = nom_match;
        this.date_match = date_match;
        this.duree_match = duree_match;
        this.tournois = tournois;
        this.equipe = equipe;
        this.equipe1 = equipe1;
    }

    public Matchs(int id_match, String nom_match, Date date_match, String duree_match, Tournois tournois, Equipe equipe, Equipe equipe1) {
        this.id_match = id_match;
        this.nom_match = nom_match;
        this.date_match = date_match;
        this.duree_match = duree_match;
        this.tournois = tournois;
        this.equipe = equipe;
        this.equipe1 = equipe1;
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

    public Date getDate_match() {
        return date_match;
    }

    public void setDate_match(Date date_match) {
        this.date_match = date_match;
    }

    public String getDuree_match() {
        return duree_match;
    }

    public void setDuree_match(String duree_match) {
        this.duree_match = duree_match;
    }

    public Tournois getTournois() {
        return tournois;
    }

    public void setTournois(Tournois tournois) {
        this.tournois = tournois;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    public Equipe getEquipe1() {
        return equipe1;
    }

    public void setEquipe1(Equipe equipe1) {
        this.equipe1 = equipe1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matchs matchs = (Matchs) o;
        return id_match == matchs.id_match && Objects.equals(nom_match, matchs.nom_match) && Objects.equals(date_match, matchs.date_match) && Objects.equals(duree_match, matchs.duree_match) && Objects.equals(tournois, matchs.tournois) && Objects.equals(equipe, matchs.equipe) && Objects.equals(equipe1, matchs.equipe1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_match, nom_match, date_match, duree_match, tournois, equipe, equipe1);
    }

    @Override
    public String toString() {
        return "Matchs{" +
                "id_match=" + id_match +
                ", nom_match='" + nom_match + '\'' +
                ", date_match=" + date_match +
                ", duree_match='" + duree_match + '\'' +
                ", tournois=" + tournois +
                ", equipe=" + equipe +
                ", equipe1=" + equipe1 +
                '}';
    }
}
