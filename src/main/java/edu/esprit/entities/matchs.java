package edu.esprit.entities;

import java.util.Objects;

public class matchs {

    private int id_match;
    private String date_matche;
    private String duree_match;

    public matchs(String date_matche, String duree_match) {
        this.date_matche = date_matche;
        this.duree_match = duree_match;
    }

    public matchs(int id_match, String date_matche, String duree_match) {
        this.id_match = id_match;
        this.date_matche = date_matche;
        this.duree_match = duree_match;
    }

    public int getId_match() {
        return id_match;
    }

    public void setId_match(int id_match) {
        this.id_match = id_match;
    }

    public String getDate_matche() {
        return date_matche;
    }

    public void setDate_matche(String date_matche) {
        this.date_matche = date_matche;
    }

    public String getDuree_match() {
        return duree_match;
    }

    public void setDuree_match(String duree_match) {
        this.duree_match = duree_match;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        matchs matchs = (matchs) o;
        return id_match == matchs.id_match && Objects.equals(date_matche, matchs.date_matche) && Objects.equals(duree_match, matchs.duree_match);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_match, date_matche, duree_match);
    }

    @Override
    public String toString() {
        return "matchs{" +
                "id_match=" + id_match +
                ", date_matche='" + date_matche + '\'' +
                ", duree_match='" + duree_match + '\'' +
                '}';
    }
}
