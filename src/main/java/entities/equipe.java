
package entities;

import java.util.Objects;

public class equipe {
    private int id;
    private String nom;
    private int id_joueur;


    public equipe() {

    }

    public equipe(int id, String nom, int id_joueur) {
        this.id = id;
        this.nom = nom;
        this.id_joueur = id_joueur;
    }

    public equipe(String nom, int id_joueur) {
        this.nom = nom;
        this.id_joueur = id_joueur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getId_joueur() {
        return id_joueur;
    }

    public void setId_joueur(int id_joueur) {
        this.id_joueur = id_joueur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        equipe equipe = (equipe) o;
        return id == equipe.id && id_joueur == equipe.id_joueur && Objects.equals(nom, equipe.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, id_joueur);
    }
}