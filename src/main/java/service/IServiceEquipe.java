package service;

import entities.AvisJoueur;
import entities.User;

import java.sql.SQLException;
import java.util.List;

public interface IServiceEquipe <Equipe>{

    void ajouter(Equipe av, User us) throws SQLException;

    public void modifier(int id, Equipe equipeModifie,int idjoueur) throws SQLException ;

    void supprimer(int id) throws SQLException ;

    List<Equipe> recuperer() throws SQLException;

}
