package entities;
import java.sql.Date;


public class Reservation {
    public int id,id_stade,id_PremiereEquipe,id_DeuxiemeEquipe,id_organisateur;
    public String NomStade;
    public Date date;


    public Reservation(int id, int id_stade, int id_PremiereEquipe, int id_DeuxiemeEquipe, int id_organisateur, Date date) {
        this.id = id;
        this.id_stade = id_stade;
        this.id_PremiereEquipe = id_PremiereEquipe;
        this.id_DeuxiemeEquipe = id_DeuxiemeEquipe;
        this.id_organisateur = id_organisateur;
        this.date = date;
    }

    public Reservation() {
    }

    public Reservation(int id_stade, int id_PremiereEquipe, int id_DeuxiemeEquipe, int id_organisateur, Date date) {
        this.id_stade = id_stade;
        this.id_PremiereEquipe = id_PremiereEquipe;
        this.id_DeuxiemeEquipe = id_DeuxiemeEquipe;
        this.id_organisateur = id_organisateur;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomStade() {
        return NomStade;
    }

    public void setNomStade(String nomStade) {
        NomStade = nomStade;
    }

    public int getId_stade() {
        return id_stade;
    }

    public void setId_stade(int id_stade) {
        this.id_stade = id_stade;
    }

    public int getId_PremiereEquipe() {
        return id_PremiereEquipe;
    }

    public void setId_PremiereEquipe(int id_PremiereEquipe) {
        this.id_PremiereEquipe = id_PremiereEquipe;
    }

    public int getId_DeuxiemeEquipe() {
        return id_DeuxiemeEquipe;
    }

    public void setId_DeuxiemeEquipe(int id_DeuxiemeEquipe) {
        this.id_DeuxiemeEquipe = id_DeuxiemeEquipe;
    }

    public int getId_organisateur() {
        return id_organisateur;
    }

    public void setId_organisateur(int id_organisateur) {
        this.id_organisateur = id_organisateur;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", id_stade=" + id_stade +
                ", id_PremiereEquipe=" + id_PremiereEquipe +
                ", id_DeuxiemeEquipe=" + id_DeuxiemeEquipe +
                ", id_organisateur=" + id_organisateur +
                ", date=" + date +
                '}';
    }
}
