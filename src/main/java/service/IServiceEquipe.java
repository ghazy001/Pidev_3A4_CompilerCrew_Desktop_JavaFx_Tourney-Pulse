package service;

import entities.AvisJoueur;
import entities.Equipe;
import entities.User;

import java.sql.SQLException;
import java.util.List;

public interface IServiceEquipe <Equipe>{

    void ajouter(Equipe av) throws SQLException;

    public void modifier(int id, Equipe equipeModifie) throws SQLException ;

    void supprimer(int id) throws SQLException ;

    List<Equipe> recuperer() throws SQLException;
    void affecter(Equipe equipe,User user)throws SQLException;

}
