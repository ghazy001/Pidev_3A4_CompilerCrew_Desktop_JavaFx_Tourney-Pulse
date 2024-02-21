package edu.esprit.Services;

public interface IService<R> {
    void ajouter(R t);
    void modifier(int id,R t);
    void supprimer(int id);


    void afficher();

}
