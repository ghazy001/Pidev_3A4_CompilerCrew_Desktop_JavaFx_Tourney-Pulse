package edu.esprit.services;

public interface InterfaceService<T> {
    void ajouter(T t);
    void modifier(int id,T t);
    void supprimer(int id);
    void afficher();
}
