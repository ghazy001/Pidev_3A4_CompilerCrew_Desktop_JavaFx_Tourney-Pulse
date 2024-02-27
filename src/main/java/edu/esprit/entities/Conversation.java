package edu.esprit.entities;


import java.sql.Date;

public class Conversation {
    public int id,id_user,id_reclamation;
    public String message;
    public Date dateMsg;
    public String etat;

    public Conversation() {
    }

    public Conversation(int id, int id_user, int id_reclamation, String message, Date dateMsg, String etat) {
        this.id = id;
        this.id_user = id_user;
        this.id_reclamation = id_reclamation;
        this.message = message;
        this.dateMsg = dateMsg;
        this.etat = etat;
    }

    public Conversation(int id_user, int id_reclamation, String message, Date dateMsg, String etat) {
        this.id_user = id_user;
        this.id_reclamation = id_reclamation;
        this.message = message;
        this.dateMsg = dateMsg;
        this.etat = etat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_reclamation() {
        return id_reclamation;
    }

    public void setId_reclamation(int id_reclamation) {
        this.id_reclamation = id_reclamation;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDateMsg() {
        return dateMsg;
    }

    public void setDateMsg(Date dateMsg) {
        this.dateMsg = dateMsg;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    @Override
    public String toString() {
        return "Conversation{" +
                "id=" + id +
                ", id_user=" + id_user +
                ", id_reclamation=" + id_reclamation +
                ", message='" + message + '\'' +
                ", dateMsg=" + dateMsg +
                ", etat=" + etat +
                '}';
    }
}
