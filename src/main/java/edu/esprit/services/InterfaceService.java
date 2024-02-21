package edu.esprit.services;

import java.sql.SQLException;
import java.util.Set;
public interface InterfaceService<T> {
    public void ajouter(T t) throws SQLException;
    public void modifier(int id, T t) throws SQLException;
    public void supprimer(int id) throws SQLException;
    public void afficher() throws SQLException;
    public T getOneById(int id) throws SQLException;
    public Set<T> getAll() throws SQLException;
}
