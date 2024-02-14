package service;

import entities.AvisJoueur;
import entities.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface IServiceAvisJoueur<AvisJoueur> {

    void ajouter(AvisJoueur av, User us) throws SQLException ;

    public void modifier(int id, AvisJoueur avisModifie,int idr) throws SQLException ;

    void supprimer(int id) throws SQLException ;

    List<entities.AvisJoueur> recuperer() throws SQLException;

    boolean existeAvis(int id) throws SQLException;
}
