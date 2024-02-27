package edu.esprit.test;

import edu.esprit.entities.Matchs;
import edu.esprit.entities.Tournois;
import edu.esprit.entities.Equipe;
import edu.esprit.services.ServiceMatch;
import edu.esprit.services.ServiceTournois;
import edu.esprit.utils.DataSource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



public class Main {
    public static void main(String[] args) throws ParseException {

        DataSource.getInsatnce().getConnection();

        System.out.println("*********** Les Tournois ******************");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateD = sdf.parse("2024-05-11");
        java.sql.Date sqlDateD = new java.sql.Date(dateD.getTime());

        Date dateF = sdf.parse("2024-05-20");
        java.sql.Date sqlDateF = new java.sql.Date(dateF.getTime());

        Tournois t = new Tournois("La Ligua","Tunis Rue Habib Borguiba", 16, sqlDateD, sqlDateF);
        ServiceTournois servicee = new ServiceTournois();

        servicee.ajouter(t);
        servicee.supprimer(5);

        Date dateD1 = sdf.parse("2024-05-30");
        java.sql.Date sqlDateD1 = new java.sql.Date(dateD1.getTime());

        Date dateF1 = sdf.parse("2024-05-20");
        java.sql.Date sqlDateF1 = new java.sql.Date(dateF1.getTime());

        Tournois t1 = new Tournois("Champions","Ariana", 20, sqlDateD1, sqlDateF1);
        servicee.modifier(t1);
        servicee.afficher();

        System.out.println("*********** Les Matchs ******************");

        Date dateM = sdf.parse("2024-10-10");
        java.sql.Date sqlDateM = new java.sql.Date(dateM.getTime());

        Tournois tournoisForMatch = new Tournois();
        tournoisForMatch.setId_tournois(1);
        Equipe equipe1 = new Equipe();
        Equipe equipe2 = new Equipe();
        equipe1.setId_equipe(2);
        equipe2.setId_equipe(3);

        Matchs m = new Matchs("Demi Final",sqlDateM,"90 min",tournoisForMatch,equipe1,equipe2);
        ServiceMatch service = new ServiceMatch();

        service.ajouter(m);
        service.supprimer(4);

        Date dateM1 = sdf.parse("2024-08-03");
        java.sql.Date sqlDateM1 = new java.sql.Date(dateM1.getTime());

        Tournois tournoisForMatch1 = new Tournois();
        tournoisForMatch1.setId_tournois(2);
        Equipe equipe3 = new Equipe();
        Equipe equipe4 = new Equipe();
        equipe3.setId_equipe(2);
        equipe4.setId_equipe(3);

        Matchs m1 = new Matchs("Final",sqlDateM1,"90 min",tournoisForMatch1,equipe3,equipe4);
        service.modifier(m1);
        service.afficher();

    }
}