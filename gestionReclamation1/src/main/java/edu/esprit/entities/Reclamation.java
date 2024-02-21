package edu.esprit.entities;
import java.sql.Date;

import java.util.Objects;



public class Reclamation {
    private int id_rec,id;

    private String email, object, rec;
    Date date_rec;

    public Reclamation(String email, String object, String rec, Date date_rec, int id) {
        this.email = email;
        this.object = object;
        this.rec = rec;
        this.date_rec = Date.valueOf(String.valueOf(date_rec));
        this.id=id;
    }


    public Reclamation() {
    }
/*
    public Reclamation(int id_rec,String email, String object, String rec, String date_rec,int id) {
        this.id_rec = id_rec;
        this.email = email;
        this.object = object;
        this.rec = rec;
        this.date_rec = Date.valueOf(date_rec);
        this.id = id;

    }

 */

    public Reclamation(int id_rec, int id, String email, String object, String rec, Date date_rec) {
        this.id_rec = id_rec;
        this.id = id;
        this.email = email;
        this.object = object;
        this.rec = rec;
        this.date_rec = date_rec;
    }

    public int getId_rec() {
        return id_rec;
    }

    public void setId_rec(int id_rec) {
        this.id_rec = id_rec;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getRec() {
        return rec;
    }

    public void setReclamation(String reclamation) {
        this.rec = rec;
    }

    public Date getDate_rec() {
        return date_rec;
    }

    public void setDate_rec(String date_rec) {
        this.date_rec = Date.valueOf(date_rec);
    }

    @Override
    public String toString() {
        return "reclamation{" +
                "id_rec=" + id_rec +
                ", id=" + id +
                ", email='" + email + '\'' +
                ", object='" + object + '\'' +
                ", rec='" + rec + '\'' +
                ", date_rec=" + date_rec +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reclamation that = (Reclamation) o;
        return id_rec == that.id_rec && id == that.id && Objects.equals(email, that.email) && Objects.equals(object, that.object) && Objects.equals(rec, that.rec) && Objects.equals(date_rec, that.date_rec);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_rec, id, email, object, rec, date_rec);
    }
}
