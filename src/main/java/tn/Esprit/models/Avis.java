package tn.Esprit.models;

import java.sql.Timestamp;

public class Avis {
    private int idAvis;
    private int idProd;
    private Timestamp dateAvis;
    private int note;
    private User user; // Reference to the user who created the response

    public Avis(int idAvis, int idProd, Timestamp dateAvis, int note, User user) {
        this.idAvis = idAvis;
        this.idProd = idProd;
        this.dateAvis = dateAvis;
        this.note = note;
        this.user = user; // Set the user who created the response
    }
    public Avis(int idAvis, int idProd, Timestamp dateAvis, int note) {
        this.idAvis = idAvis;
        this.idProd = idProd;
        this.dateAvis = dateAvis;
        this.note = note;

    }

    public Avis() {

    }

    // Getters and setters for user
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public int getIdAvis() {
        return idAvis;
    }

    public void setIdAvis(int idAvis) {
        this.idAvis = idAvis;
    }

    public int getIdProd() {
        return idProd;
    }

    public void setIdProd(int idProd) {
        this.idProd = idProd;
    }

    public Timestamp getdateAvis() {
        return dateAvis;
    }

    public void setdateAvis(Timestamp dateAvis) {
        this.dateAvis = dateAvis;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Avis{" +
                "idAvis=" + idAvis +
                ", idProd=" + idProd +
                ", dateAvis=" + dateAvis +
                ", note='" + note + '\'' +
                '}';
    }
}
