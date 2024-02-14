
package entities;

import java.util.Date;

public class Equipe {
    private int id;
    private String nom;
    private User user;
    private String image;


    public Equipe() {

    }

    public Equipe(int id, String nom, User user, String image) {
        this.id = id;
        this.nom = nom;
        this.user = user;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public User getUser() {
        return user;
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

    public void setUser(User user) {
        this.user = user;
    }


    public void setImage(String image) {
        this.image = image;
    }
}