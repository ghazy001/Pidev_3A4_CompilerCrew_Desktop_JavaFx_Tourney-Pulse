package entities;

public class Stade {
    public int id,Capacity,Numero;
    public String Nom,Lieu;

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
