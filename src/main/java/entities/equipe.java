
package entities;

import java.util.Objects;

public class equipe {
    private int id;
    private String nom;
    private String Nom_joueur;

    public equipe(String nom, String nom_joueur) {
        this.nom = nom;
        Nom_joueur = nom_joueur;
    }

    public equipe(int id, String nom, String nom_joueur) {
        this.id = id;
        this.nom = nom;
        Nom_joueur = nom_joueur;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getNom_joueur() {
        return Nom_joueur;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setNom_joueur(String nom_joueur) {
        Nom_joueur = nom_joueur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        equipe equipe = (equipe) o;
        return id == equipe.id && Objects.equals(nom, equipe.nom) && Objects.equals(Nom_joueur, equipe.Nom_joueur);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, Nom_joueur);
    }

    @Override
    public String toString() {
        return "equipe{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", Nom_joueur='" + Nom_joueur + '\'' +
                '}';
    }

}