package edu.esprit.entities;

import java.sql.Date;

public class Messagerie {
    public int id_mess, id_rec;

    public String contenue;
    public Date temps;

    public Messagerie(int id_mess, int id_rec, String contenue, Date temps) {
        this.id_mess = id_mess;
        this.contenue = contenue;
        this.temps = temps;
        this.id_rec = id_rec;

    }

    public Messagerie( int id_rec,String contenue, Date temps) {
        this.id_rec = id_rec;

        this.contenue = contenue;
        this.temps = temps;

    }


    public int getId_mess() {
        return id_mess;
    }

    public void setId_mess(int id_mess) {
        this.id_mess = id_mess;
    }

    public int getId_rec() {
        return id_rec;
    }

    public void setId_rec(int id_rec) {
        this.id_rec = id_rec;
    }


    public String getContenue() {
        return contenue;
    }

    public void setContenue(String contenue) {
        this.contenue = contenue;
    }

    public Date getTemps() {
        return temps;
    }

    public void setTemps(Date temps) {
        this.temps = temps;
    }


    @Override
    public String toString() {
        return "messagerie{" +
                "id_mess=" + id_mess +
                ", id_rec=" + id_rec +
                ", contenue='" + contenue + '\'' +
                ", temps=" + temps +
                '}';
    }
}
