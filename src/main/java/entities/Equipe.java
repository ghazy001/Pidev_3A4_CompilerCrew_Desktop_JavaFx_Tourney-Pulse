
package entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Equipe {
    private int id;
    private String nom;
    private List<User> jouers=new ArrayList<>();
    private Date dateCreation;
    private String image;


    public Equipe() {


    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Equipe(int id, String nom, Date dateCreation, String image) {
        this.id = id;
        this.nom = nom;
        this.dateCreation = dateCreation;
        this.image = image;

    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }




    public String getImage() {
        return image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<User> getJouers() {
        return jouers;
    }

    public void setJouers(List<User> jouers) {
        this.jouers = jouers;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipe equipe = (Equipe) o;
        return id == equipe.id;
    }


}