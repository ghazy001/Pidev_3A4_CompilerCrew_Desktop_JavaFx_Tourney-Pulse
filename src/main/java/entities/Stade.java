package entities;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Stade {
    private int id,Capacity,Numero;
    private String Nom,Lieu;
    private ImageView imageIntiale;

    public Stade() {
    }

    public Stade(int id, int capacity, int numero, String nom, String lieu) {
        this.id = id;
        Capacity = capacity;
        Numero = numero;
        Nom = nom;
        Lieu = lieu;
    }

    public Stade(int capacity, int numero, String nom, String lieu) {
        Capacity = capacity;
        Numero = numero;
        Nom = nom;
        Lieu = lieu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCapacity() {
        return Capacity;
    }

    public void setCapacity(int capacity) {
        Capacity = capacity;
    }

    public int getNumero() {
        return Numero;
    }

    public void setNumero(int numero) {
        Numero = numero;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getLieu() {
        return Lieu;
    }

    public void setLieu(String lieu) {
        Lieu = lieu;
    }

    public ImageView getImageIntiale() {
        return imageIntiale;
    }

    public void setImageIntiale(String url) {
        this.imageIntiale = new ImageView(new Image(url));
        this.imageIntiale.setFitHeight(200);
        this.imageIntiale.setFitWidth(200);
    }

    @Override
    public String toString() {
        return "Stade{" +
                "id=" + id +
                ", Capacity=" + Capacity +
                ", Numero=" + Numero +
                ", Nom='" + Nom + '\'' +
                ", Lieu='" + Lieu + '\'' +
                '}';
    }
}
