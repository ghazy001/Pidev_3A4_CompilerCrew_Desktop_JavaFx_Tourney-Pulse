package edu.esprit.services;

import java.util.List;
public interface InterfaceService<T> {
    public void ajouter(T t);
    public void modifier(T t);
    public void supprimer(int id);
    public void afficher();
    public T getOneById(int id);
    public List<T> getAll();
}
