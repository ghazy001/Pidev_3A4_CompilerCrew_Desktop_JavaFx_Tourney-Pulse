package edu.esprit.Services;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface IService<R> {
    void ajouter(R t);
    void modifier(int id,R t);
    void supprimer(int id);

    //public List<R> getAll() ;

    void afficher();

}
