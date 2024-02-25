package entities;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
public class AvisJoueur {
    private int idAvis;
    private User user;
    private String commentaire;
    private LocalDate dateAvis;

    private float note;

    public AvisJoueur() {
    }

    public AvisJoueur(int idAvis, User user, String commentaire, LocalDate dateAvis, float note) {
        this.idAvis = idAvis;
        this.user = user;
        this.commentaire = commentaire;
        this.dateAvis = dateAvis;
        this.note = note;
    }

    public int getIdAvis() {
        return idAvis;
    }

    public User getUser() {
        return user;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public LocalDate getDateAvis() {
        return dateAvis;
    }

    public float getNote() {
        return note;
    }

    public void setIdAvis(int idAvis) {
        this.idAvis = idAvis;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public void setDateAvis(LocalDate dateAvis) {
        this.dateAvis = dateAvis;
    }

    public void setNote(float note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "AvisJoueur{" + "idAvis=" + idAvis + ", user=" + user + ", commentaire=" + commentaire + ", dateAvis=" + dateAvis + ", note=" + note + '}';
    }}