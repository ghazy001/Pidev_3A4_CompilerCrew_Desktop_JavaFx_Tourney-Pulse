package entities;

import java.util.Objects;

public class AvisJoueur {
    private int idAvis;
    private int idJoueur;
    private String commentaire;
    private float note;

    private String nom_joueur;

    public AvisJoueur(int idAvis, int idJoueur, String commentaire, float note, String nom_joueur) {
        this.idAvis = idAvis;
        this.idJoueur = idJoueur;
        this.commentaire = commentaire;
        this.note = note;
        this.nom_joueur = nom_joueur;
    }

    public AvisJoueur(String commentaire, float note, String nom_joueur) {
        this.commentaire = commentaire;
        this.note = note;
        this.nom_joueur = nom_joueur;
    }

    public int getIdAvis() {
        return idAvis;
    }

    public void setIdAvis(int idAvis) {
        this.idAvis = idAvis;
    }

    public int getIdJoueur() {
        return idJoueur;
    }

    public void setIdJoueur(int idJoueur) {
        this.idJoueur = idJoueur;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public float getNote() {
        return note;
    }

    public void setNote(float note) {
        this.note = note;
    }

    public String getNom_joueur() {
        return nom_joueur;
    }

    public void setNom_joueur(String nom_joueur) {
        this.nom_joueur = nom_joueur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AvisJoueur that = (AvisJoueur) o;
        return idAvis == that.idAvis && idJoueur == that.idJoueur && Float.compare(note, that.note) == 0 && Objects.equals(commentaire, that.commentaire) && Objects.equals(nom_joueur, that.nom_joueur);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAvis, idJoueur, commentaire, note, nom_joueur);
    }

    @Override
    public String toString() {
        return "AvisJoueur{" +
                "idAvis=" + idAvis +
                ", idJoueur=" + idJoueur +
                ", commentaire='" + commentaire + '\'' +
                ", note=" + note +
                ", nom_joueur='" + nom_joueur + '\'' +
                '}';
    }
}
